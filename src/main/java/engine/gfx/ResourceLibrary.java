package engine.gfx;

import engine.core.utils.Dict;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceLibrary {
    private Dict<String, Dict> files;
    private final static String ROOT_FILE = ".";

    public ResourceLibrary(){
        this.files = new Dict<>();
        loadTreeFromRoot(ROOT_FILE, ROOT_FILE, files);
    }

    private void loadTreeFromRoot(String parent, String pathToChild, Dict parentDict) {
        String[] childFolders = getChildFolders(pathToChild);
        File[] filesInFolder = getFilesInFolder(pathToChild);

        if (childFolders.length > 0) {
            for (String childName : childFolders) {
                Dict<String, Dict> childNode = new Dict<>();
                parentDict.put(childName, childNode);
                loadTreeFromRoot(childName, pathToChild + "/" + childName, childNode);
            }
        } if (filesInFolder.length > 0) {
            for (File file : filesInFolder) {
                parentDict.put(
                        getFileNameWithoutFileType(file.getName(), "."),
                        file);
            }
        }
    }


    private String getFileNameWithoutFileType(String sheetName, String delimiter) {
        return sheetName.substring(0, sheetName.indexOf(delimiter));
    }

    private File[] getFilesInFolder(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file;
        try {
            file = new File(url != null ? url.toURI() : null);
            return file.listFiles(File::isFile);
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file.listFiles(File::isFile);
    }

    private String[] getChildFolders(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file;
        try {
            file = new File(url != null ? url.toURI() : null);
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file.list((current,name) -> new File(current, name).isDirectory());
    }

    public File getFileFromKey(String path, String keyName){
        return getFilesFromTreeNode(path).key(keyName);
    }
    public Dict<String, File> getFilesFromTreeNode(String path) {
        String[] dir = path.split("/");
        Dict v = files.key(dir[0]);
        for (int i = 1; i < dir.length; i++) {
            v = (Dict) v.key(dir[i]);
        }

        return v;

    }
}
