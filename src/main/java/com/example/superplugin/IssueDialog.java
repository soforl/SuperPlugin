package com.example.superplugin;

import kotlinx.serialization.json.Json;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;



public class IssueDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextArea textArea1;
    private JLabel Title;
    private JLabel Description;

    public IssueDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        IssueDialog dialog = new IssueDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void TakeIssue(String code){
        String full = "https://api.github.com/repos/soforl/SuperPlugin/issues";
        String token;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:\\tmp\\SuperPlugin\\src\\main\\java\\com\\example\\superplugin\\tokk"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            token = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        HttpClient client = HttpClient.newHttpClient();

        String body = textArea1.getText() + "<br> ```"  + code + "```";

        String jsonString = new JSONObject()
                .put("title", textField1.getText())
                .put("body", body)
                .toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(full))
                .setHeader("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
