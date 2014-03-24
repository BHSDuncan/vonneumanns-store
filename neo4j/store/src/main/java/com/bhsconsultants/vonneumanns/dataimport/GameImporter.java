package com.bhsconsultants.vonneumanns.dataimport;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Collections;
import java.util.Map;

/**
 * @author mh
 * @since 04.10.11
 */
public class GameImporter {

    private final GameDbImportService importer;

    public static void main(String[] args) {
        //final FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");
        final FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml");
        try {
            final GameDbImportService importer = ctx.getBean(GameDbImportService.class);
            final GameImporter GameImporter = new GameImporter(importer);
            GameImporter.runImport(getGameIdsToImport(args));
        } finally {
            ctx.close();
        }
    }

    public GameImporter(GameDbImportService importer) {
        this.importer = importer;
    }

    private void runImport(Map<Integer, Integer> GameIdsToImport) {
        final long start = System.currentTimeMillis();
        final Map<Integer, String> result = importer.importGames(GameIdsToImport);
        final long time = System.currentTimeMillis() - start;
        for (Map.Entry<Integer, String> Game : result.entrySet()) {
            System.out.println(Game.getKey() + "\t" + Game.getValue());
        }
        System.out.println("Imported Games took "+ time+" ms.");

    
    }

    private static Map<Integer, Integer> getGameIdsToImport(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Usage: GameImporter 1 10000\nWorking Directory should be the directory with the json files in data/json.");
        }
        if (args.length == 1) {
            return Collections.singletonMap(Integer.valueOf(args[0]), Integer.valueOf(args[0]));
        }
        return Collections.singletonMap(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
    }
}
