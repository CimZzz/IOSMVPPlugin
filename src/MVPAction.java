import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MVPAction extends AnAction {
    static String userName;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);

        if(project == null || editor == null)
            return;

        PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);

        if(currentEditorFile == null)
            return;

        HashMap<String, String> dataMap = new InputDialog().getData();
        if (dataMap != null) {
            PsiDirectory directory = currentEditorFile.isDirectory() ? (PsiDirectory) currentEditorFile : currentEditorFile.getParent();
            if(directory != null) {
                String name = dataMap.get("name");
                String user = dataMap.get("user");

                userName = user;

                String uName = name + "UI";
                String pName = name + "Presenter";
                String mName = name + "Method";


                PrintWriter writer = null;

                try {
                    PsiFile uiFile = directory.createFile(uName + ".swift");
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
                    writer = new PrintWriter(new File(uiFile.getVirtualFile().getPath()));
                    writer.println("//");
                    writer.println("// Created by " + user + " on " + date + ".");
                    writer.println("// Copyright (c) 2019 com.taomanfen.apps.ios. All rights reserved.");
                    writer.println("//");
                    writer.println();
                    writer.println("import Foundation");
                    writer.println("import UIKit");
                    writer.println();
                    writer.println("class " + uName + ": BasePresenterViewController<" + pName + "> {");
                    writer.println("\toverride func generatePresenter() -> " + pName + "? {");
                    writer.println("\t\treturn " + pName + ".init(self)");
                    writer.println("\t}");
                    writer.println("}");
                    writer.flush();
                    writer.close();


                    PsiFile pFile = directory.createFile(pName + ".swift");
                    writer = new PrintWriter(new File(pFile.getVirtualFile().getPath()));
                    writer.println("//");
                    writer.println("// Created by " + user + " on " + date + ".");
                    writer.println("// Copyright (c) 2019 com.taomanfen.apps.ios. All rights reserved.");
                    writer.println("//");
                    writer.println();
                    writer.println("import Foundation");
                    writer.println("import UIKit");
                    writer.println();
                    writer.println("class " + pName + ": BasePresenter {");
                    writer.println("\tlet view: " + uName);
                    writer.println("\tlet method: " + mName + " = " + mName + ".init()");
                    writer.println();
                    writer.println("\tinit(_ view: " + uName + ") {");
                    writer.println("\t\tself.view = view");
                    writer.println("\t}");
                    writer.println();
                    writer.println("\toverride func onInit() {");
                    writer.println("\t\tmethod.ref = self");
                    writer.println("\t}");
                    writer.println("\toverride func onDestroy() {");
                    writer.println("\t\tsuper.onDestroy()");
                    writer.println("\t\tmethod.onDestroy()");
                    writer.println("\t}");
                    writer.println("}");
                    writer.flush();
                    writer.close();

                    PsiFile mFile = directory.createFile(mName + ".swift");
                    writer = new PrintWriter(new File(mFile.getVirtualFile().getPath()));
                    writer.println("//");
                    writer.println("// Created by " + user + " on " + date + ".");
                    writer.println("// Copyright (c) 2019 com.taomanfen.apps.ios. All rights reserved.");
                    writer.println("//");
                    writer.println();
                    writer.println("import Foundation");
                    writer.println("import UIKit");
                    writer.println();
                    writer.println("class " + mName + ": BaseMethod<" + pName + "> {");
                    writer.println("\toverride func onDestroy() {");
                    writer.println("\t\tsuper.onDestroy()");
                    writer.println("\t}");
                    writer.println("}");
                    writer.flush();
                    writer.close();

                }
                catch (Exception e) {

                }
                finally {
                    if(writer != null) {
                        try {
                            writer.close();
                        } catch (Exception e) {

                        }
                    }
                }

            }
        }
    }


    public static class InputDialog extends JDialog {
        private HashMap<String, String> dataMap;

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
            confirmBtn.addActionListener(e -> {
                String name = nameField.getText();
                String user = userField.getText();

                if(name == null || (name = name.trim()).length() == 0) {
                    Utils.showErrorMessage("前缀不能为空");
                    return;
                }

                if(user == null || (user = user.trim()).length() == 0) {
                    Utils.showErrorMessage("用户名不能为空");
                    return;
                }

                dataMap = new HashMap<>();
                dataMap.put("name", name);
                dataMap.put("user", user);

                dispose();
            });
            JButton cancelBtn = new JButton("取消");
            cancelBtn.setBounds(125, 55, 60, 30);
            cancelBtn.addActionListener(e -> dispose());

            container.add(nameLabel);
            container.add(nameField);
            container.add(userLabel);
            container.add(userField);
            container.add(confirmBtn);
            container.add(cancelBtn);

            setVisible(true);
        }


        public HashMap<String, String> getData() {
            return dataMap;
        }
    }

}
