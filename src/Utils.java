import com.intellij.openapi.ui.Messages;

public final class Utils {
    public static void showErrorMessage(String errorText) {
        Messages.showErrorDialog(errorText, "错误");
    }
}
