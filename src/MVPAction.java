import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MVPAction extends AnAction {
    static String userName;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);

//        project.getUserData()
        System.out.println(currentEditorFile.getClass().getSimpleName());
//        new JFrame().setVisible(true);

        new InputDialog().
    }


    public static class InputDialog extends JDialog {
        private String prefixName;
        private String path;

        public InputDialog() {
            setModal(true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setSize(260, 110);
            setLayout(null);

            Container container = getContentPane();

            JLabel nameLabel = new JLabel("前缀:");
            nameLabel.setBounds(5, 5, 60, 20);
            JTextField nameField = new JTextField();
            nameField.setBounds( 70, 5, 190, 20);

            JLabel userLabel = new JLabel("用户名:");
            userLabel.setBounds(5, 30, 60, 20);
            JTextField userField = new JTextField();
            userField.setBounds( 70, 30, 190, 20);
            userField.setText(userName);

            JButton confirmBtn = new JButton("确认");
            confirmBtn.setBounds(190, 55, 60, 30);
            JButton cancelBtn = new JButton("取消");
            cancelBtn.setBounds(125, 55, 60, 30);
            cancelBtn.addActionListener(e -> {
                dispose();
            });

            container.add(nameLabel);
            container.add(nameField);
            container.add(userLabel);
            container.add(userField);
            container.add(confirmBtn);
            container.add(cancelBtn);

            setVisible(true);
        }


        public String getPrefixName() {
            return prefixName;
        }

        public String getPath() {
            return path;
        }
    }

}
