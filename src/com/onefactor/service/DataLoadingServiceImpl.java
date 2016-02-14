package com.onefactor.service;

import com.onefactor.config.SettingsFactory;
import com.onefactor.dmodel.MapCell;
import com.onefactor.dmodel.MapCellPK;
import com.onefactor.dmodel.Placemark;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: Yaroslav Frolikov
 * Date: 15.02.16 1:35
 */
public class DataLoadingServiceImpl implements DataLoadingService {
    private static final Logger log = LoggerFactory.getLogger(DataLoadingServiceImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    @PostConstruct
    void loadData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("Data loading started");

        StatelessSession session = sessionFactory.openStatelessSession();
        Transaction tx = session.beginTransaction();

        Path placemarksPath = Paths.get(SettingsFactory.INSTANCE.getSettings().getPlacemarksFilename());
        Path mapCellsPath = Paths.get(SettingsFactory.INSTANCE.getSettings().getMapCellsFilename());

        try {
            /***** Чтение меток *****/
            log.info("Loading placemarks");
            BufferedReader br = Files.newBufferedReader(placemarksPath, Charset.forName("UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                Placemark placemark = readPlacemarkLine(line);

                if (placemark != null) {
                    session.insert(placemark);
                }
            }
            br.close();

            /***** Чтение данных о ячейках *****/
            log.info("Loading map tiles");
            br = Files.newBufferedReader(mapCellsPath, Charset.forName("UTF-8"));
            while ((line = br.readLine()) != null) {
                MapCell mapCell = readMapCellLine(line);

                if (mapCell != null) {
                    session.insert(mapCell);
                }
            }
            br.close();

            tx.commit();

            stopWatch.stop();
            log.info(String.format("Data loading finished after %d ms", stopWatch.getTotalTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
    }

    public Placemark readPlacemarkLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) {
            return null;
        }

        Placemark placemark = new Placemark();

        Long id = Long.valueOf(parts[0].trim());
        placemark.setUserId(id);

        Double lat = Double.valueOf(parts[1].trim());
        placemark.setLat(lat);

        Double lon = Double.valueOf(parts[2].trim());
        placemark.setLon(lon);

        return placemark;
    }

    public MapCell readMapCellLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) {
            return null;
        }

        MapCell mapCell = new MapCell();

        Integer tile_x = Integer.valueOf(parts[0].trim());
        Integer tile_y = Integer.valueOf(parts[1].trim());
        mapCell.setId(new MapCellPK(tile_x, tile_y));

        Double mean_distance = Double.valueOf(parts[2].trim());
        mapCell.setMean_distance(mean_distance);

        return mapCell;
    }
}
