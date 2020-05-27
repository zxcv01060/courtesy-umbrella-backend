package tw.edu.ntub.imd.courtesyumbrella.data.supplier;

import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.InputDataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.InvalidDataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.InvalidInputDataParameter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class UserDataSupplier implements DataSupplier {
    private static final DataSupplier INSTANCE = new UserDataSupplier();

    public static DataSupplier getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DataParameter> getRequiredValidDataList() {
        return List.of(
                new InputDataParameter("account", "test"),
                new InputDataParameter("password", "test123"),
                new InputDataParameter("email", "a@ntub.edu.tw"),
                new InputDataParameter("birthday", "2020/05/26")
        );
    }

    @Override
    public List<DataParameter> getNotRequiredValidDataList() {
        return Collections.emptyList();
    }

    @Override
    public List<InvalidDataParameter> getInvalidDataList() {
        return List.of(
                new InvalidInputDataParameter("account", "帳號 - 未填寫"),
                new InvalidInputDataParameter("account", "帳號 - 未填寫", (String) null),
                new InvalidInputDataParameter("account", "帳號 - 未填寫", ""),
                new InvalidInputDataParameter("account", "帳號 - 輸入字數大於 100 個字", "a".repeat(101)),
                new InvalidInputDataParameter("account", "帳號 - 請輸入純英文與數字", "測"),
                new InvalidInputDataParameter("account", "帳號 - 請輸入純英文與數字", "%"),
                new InvalidInputDataParameter("account", "帳號 - 請輸入純英文與數字", "@"),
                new InvalidInputDataParameter("account", "帳號 - 請輸入純英文與數字", "#"),
                new InvalidInputDataParameter("account", "帳號 - 請輸入純英文與數字", "\""),
                new InvalidInputDataParameter("account", "帳號 - 請輸入純英文與數字", "'"),
                new InvalidInputDataParameter("password", "密碼 - 未填寫"),
                new InvalidInputDataParameter("password", "密碼 - 未填寫", (String) null),
                new InvalidInputDataParameter("password", "密碼 - 未填寫", ""),
                new InvalidInputDataParameter("password", "密碼 - 請輸入純英文與數字", "測"),
                new InvalidInputDataParameter("password", "密碼 - 請輸入純英文與數字", "%"),
                new InvalidInputDataParameter("password", "密碼 - 請輸入純英文與數字", "@"),
                new InvalidInputDataParameter("password", "密碼 - 請輸入純英文與數字", "#"),
                new InvalidInputDataParameter("password", "密碼 - 請輸入純英文與數字", "\""),
                new InvalidInputDataParameter("password", "密碼 - 請輸入純英文與數字", "'"),
                new InvalidInputDataParameter("email", "信箱 - 未填寫"),
                new InvalidInputDataParameter("email", "信箱 - 未填寫", (String) null),
                new InvalidInputDataParameter("email", "信箱 - 未填寫", ""),
                new InvalidInputDataParameter("email", "信箱 - 信箱格式錯誤", "abc"),
                new InvalidInputDataParameter("birthday", "生日 - 未填寫"),
                new InvalidInputDataParameter("birthday", "生日 - 未填寫", (String) null),
                new InvalidInputDataParameter("birthday", "生日 - 未填寫", ""),
                new InvalidInputDataParameter("birthday", "生日 - 未填寫", " "),
                new InvalidInputDataParameter("birthday", "abc日期轉換失敗", "abc"),
                new InvalidInputDataParameter("birthday", "生日 - 應小於現在日期", LocalDate.now()
                        .plusDays(1)
                        .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                )
        );
    }
}
