package tw.edu.ntub.imd.birc.common.exception;

public class IgnoreException extends ProjectException {

    public IgnoreException() {
        super("理論上來說這不可能發生，但現在發生了，所以請撤銷使用此Exception，或解決BUG");
    }

    @Override
    public String getErrorCode() {
        return "Error - Imposable";
    }
}
