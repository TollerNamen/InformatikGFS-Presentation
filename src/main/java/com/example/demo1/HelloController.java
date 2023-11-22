package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class HelloController implements Initializable
{
    @FXML
    private TextArea textArea;



    @FXML
    private TreeView<String> treeView;



    @FXML
    private Label label;
    private final Map<TreeItem<String>, File> fileReference = new HashMap<>();
    private final String path = "/home/admindavid/IdeaProjects/example";

    public void setLabelText(String labelText)
    {
        label.setText(labelText);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        File file = new File(path);
        TreeItem<String> rootTreeItem = createTreeView(file.getName(), file);
        treeView.setRoot(rootTreeItem);
    }
    private TreeItem<String> createTreeView(String name, File path)
    {
        File[] subDirs = path.listFiles();
        assert subDirs != null;
        TreeItem<String> treeItem;

        if (subDirs.length == 1 && subDirs[0].isDirectory())
            return createTreeView(name + "." + subDirs[0].getName(), subDirs[0]);

        treeItem = new TreeItem<>(name);
        fileReference.put(treeItem, path);

        File[] directories = Arrays.stream(subDirs)
                .filter(File::isDirectory)
                .toArray(File[]::new);
        Arrays.sort(directories);

        File[] files = Arrays.stream(subDirs)
                .filter(file -> !file.isDirectory())
                .toArray(File[]::new);
        Arrays.sort(files);

        Stream<File> directoriesStream = Arrays.stream(directories);
        Stream<File> filesStream = Arrays.stream(files);

        File[] mergedArray = Stream.concat(directoriesStream, filesStream).toArray(File[]::new);

        for (File subDir: mergedArray)
        {
            if (subDir.isDirectory())
                treeItem.getChildren().add(createTreeView(subDir.getName(), subDir));
            else
            {
                TreeItem<String> child = new TreeItem<>(subDir.getName());
                treeItem.getChildren().add(child);
                fileReference.put(child, subDir);
            }
        }
        return treeItem;
    }
    public void onTreeViewClicked()
    {
        try
        {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem == null)
                return;

            File file = fileReference.get(selectedItem);
            if (file.isDirectory())
                return;

            String content = Files.readString(file.toPath());

            textArea.setText(content);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}