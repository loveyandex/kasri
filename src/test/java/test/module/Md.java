package test.module;

import java.io.IOException;
import java.nio.file.*;

/**
 * is created by aMIN on 10/4/2018 at 10:00 PM
 */
public class Md {
    public static void main(String[] args) throws IOException {
        final Path path = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Desktop");
        System.out.println(path);
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final WatchEvent.Kind<Path> entryModify = StandardWatchEventKinds.ENTRY_MODIFY;
            final WatchKey watchKey = path.register(watchService, entryModify);
            while (true) {
                final WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changedContex = (Path) event.context();
                    System.out.println(changedContex);

                }
                // reset the key
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregisterede");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//
//    //        Cmd.run("cmd /c start cmd /k  java -classpath run-parallel/build/libs/run-parallel-1.0-SNAPSHOT.jar org.amin.J \"god is great\" \"s\"");
//    final String s = "cmd /c start cmd /k  \"cd run-parallel && gradlew run  --args=\"god is great\" \"s\"\"";
//        Cmd.run(s);
////        String c="cmd /c start cmd /k java -Dfile.encoding=UTF-8 -classpath \"E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\charsets.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\deploy.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\access-bridge-64.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\cldrdata.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\dnsns.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\jaccess.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\jfxrt.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\localedata.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\nashorn.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\sunec.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\sunjce_provider.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\sunmscapi.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\sunpkcs11.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\ext\\zipfs.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\javaws.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\jce.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\jfr.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\jfxswt.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\jsse.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\management-agent.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\plugin.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\resources.jar;E:\\Program Files (x86)\\java\\jdk1.8.0_151\\jre\\lib\\rt.jar;F:\\apps\\jvm\\kasri\\runparallet\\target\\classes;E:\\.m2\\repository\\com\\google\\code\\gson\\gson\\2.8.5\\gson-2.8.5.jar;E:\\.m2\\repository\\Bot\\kasri\\1.0\\kasri-1.0.jar\" org.amin.J\n";
////        Cmd.run(c);
//
//    WatchService watcher = FileSystems.getDefault().newWatchService();




}
