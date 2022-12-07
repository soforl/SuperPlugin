package com.example.superplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;

public class GithubAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        IssueDialog dialog = new IssueDialog();
        dialog.pack();
        dialog.setVisible(true);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        String code = editor.getSelectionModel().getSelectedText();
        if (code != null){
            dialog.TakeIssue(code);
        }
        else{
            Messages.showMessageDialog("Selection is empty", "NewAction", Messages.getInformationIcon());
        }
    }
}
