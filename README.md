Image Finder and Copier
This Java application recursively searches for image files in a specified source directory and its subdirectories, compresses the images, and copies them to a destination directory while maintaining the directory structure.

Requirements
Java 8 or higher
The following image file extensions: jpg, jpeg, png, gif, tiff
Source and destination directories specified in the config.property file
Usage
Clone or download the repository.
Navigate to the project directory in the terminal.
Run the following command to compile the application:
bash
Copy code
javac -cp "lib/*" ImageFinderAndCopier.java
Run the application with the following command:
bash
Copy code
java -cp ".:lib/*" ImageFinderAndCopier
The application will search for image files in the source directory and its subdirectories, compress them, and copy them to the destination directory while maintaining the directory structure.
Configuration
The config.property file in the src/main/resources directory can be used to configure the source and destination directories and the image file extensions to search for.

sourceDirPath: The path to the source directory containing the images to be copied.
destDirPath: The path to the destination directory where the compressed images will be copied.
imageAvailableExtensions: A comma-separated list of image file extensions to search for.
Note: The sourceDirPath and destDirPath should be specified with double backslashes to escape the backslash character in the file path.
