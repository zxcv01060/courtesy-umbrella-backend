package tw.edu.ntub.imd.birc.common.util.bean;

import lombok.experimental.UtilityClass;
import tw.edu.ntub.imd.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.birc.common.util.bean.convert.CopyConverter;
import tw.edu.ntub.imd.birc.common.util.bean.convert.LocalDateConverter;
import tw.edu.ntub.imd.birc.common.util.bean.convert.LocalDateTimeConverter;
import tw.edu.ntub.imd.birc.common.util.bean.convert.LocalTimeConverter;

import java.util.regex.Pattern;

@UtilityClass
@SuppressWarnings("unused")
public class BeanUtils {
    private final CopyConverter<?, ?>[] converterArray;

    static {
        converterArray = new CopyConverter<?, ?>[]{
                (CopyConverter<String, Byte>) Byte::valueOf,
                (CopyConverter<String, Short>) Short::valueOf,
                (CopyConverter<String, Integer>) Integer::valueOf,
                (CopyConverter<String, Long>) Long::valueOf,
                (CopyConverter<Number, String>) String::valueOf,
                new LocalDateConverter(),
                new LocalTimeConverter(),
                new LocalDateTimeConverter()
        };
    }

    public <F, T> T copy(F copyFrom, T copyTo) throws ProjectException {
        copy(new JavaBean(copyFrom), new JavaBean(copyTo));
        return copyTo;
    }

    public void copy(JavaBean source, JavaBean target) throws ProjectException {
        copy(source, target, 0);
    }

    @SuppressWarnings("unchecked")
    private void copy(JavaBean source, JavaBean target, int copyPathIndex) throws ProjectException {
        // 遍歷source所有成員變數
        for (JavaBeanField sourceField : source.getFieldList()) {
            // 如果該變數不為null
            if (sourceField.isNotNull()) {
                // 建立該變數的@CopyTo讀取器
                CopyDescriptor sourceDescriptor = new CopyDescriptor(sourceField);
                // 遍歷target所有成員變數
                for (JavaBeanField targetField : target.getFieldList()) {
                    // 建立該變數的@CopyTo讀取器
                    CopyDescriptor targetDescriptor = new CopyDescriptor(targetField);
                    // 遍歷source變數的@CopyTo中的to變數
                    for (String copyToFieldName : sourceDescriptor.getToNameArray()) {
                        // 將to以"."切割
                        String[] copyToFieldNameArray = getCopyToFieldNameArray(copyToFieldName);
                        // 取得當前要複製的變數名稱
                        String currentCopyToFieldName = copyToFieldNameArray[Math.min(copyToFieldNameArray.length - 1, copyPathIndex)];
                        // 是否為最後一個
                        boolean isLast = copyToFieldNameArray.length - 1 == copyPathIndex;
                        // 如果當前要複製的變數名稱 = target變數的name
                        for (String name : targetDescriptor.getNameArray()) {
                            if (currentCopyToFieldName.equals(name)) {
                                if (isLast) {
                                    try {
                                        targetField.setValue(sourceField);
                                    } catch (ProjectException ignored) {
                                        for (CopyConverter copyConverter : converterArray) {
                                            try {
                                                targetField.setValue(copyConverter.convert(sourceField.getValue()));
                                                break;
                                            } catch (ClassCastException ignored1) {

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } else if (targetField.isJavaBean()) {
                                    copy(source, targetField.getJavaBean(), copyPathIndex + 1);
                                    targetField.setValue(targetField.getJavaBean()
                                                                 .getOriginalObject());
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private String[] getCopyToFieldNameArray(String copyToName) {
        if (copyToName.contains(".")) {
            return copyToName.split(Pattern.quote("."));
        } else {
            return new String[]{copyToName};
        }
    }
}