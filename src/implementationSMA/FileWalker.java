package implementationSMA;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileWalker {
	/**
	 * This attribute is static because the function walk is recursive. So, we have need for a global variable to get absolute path of java files
	 */
	public static List<String> fileList;
    public List<String> walk( String path ) {
    	
    	fileList = new ArrayList<String>();
        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return fileList;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                //System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else if (f.getName().endsWith(".java")) {
            	fileList.add(f.getAbsolutePath());
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
        return fileList;
    }
}