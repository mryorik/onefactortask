package com.onefactor.service;

import com.onefactor.config.TestConfig;
import com.onefactor.dmodel.MapCell;
import com.onefactor.dmodel.MapCellPK;
import com.onefactor.dmodel.Placemark;
import com.onefactor.dto.LocalityDTO;
import com.onefactor.dto.NewPlacemarkDTO;
import com.onefactor.dto.UsersCellStatsDTO;
import junit.framework.Assert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Yaroslav Frolikov
 * Date: 14.02.16 22:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class LocalityServiceImplTest {
    @Autowired
    LocalityService localityService;

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void testReadPlacemarkLine() throws Exception {
        DataLoadingServiceImpl dataLoadingService = new DataLoadingServiceImpl();

        Assert.assertNull(dataLoadingService.readPlacemarkLine(""));
        Assert.assertNull(dataLoadingService.readPlacemarkLine("1.0,1.0"));

        Placemark placemark = dataLoadingService.readPlacemarkLine(" 1 , 2.0 , 3.0 ");
        Assert.assertNotNull(placemark);
        Assert.assertEquals(placemark.getUserId(), new Long(1));
        Assert.assertEquals(placemark.getLat(), new Double(2.0));
        Assert.assertEquals(placemark.getLon(), new Double(3.0));
    }

    @Test
    public void testReadMapCellLine() throws Exception {
        DataLoadingServiceImpl dataLoadingService = new DataLoadingServiceImpl();

        Assert.assertNull(dataLoadingService.readMapCellLine(""));
        Assert.assertNull(dataLoadingService.readMapCellLine("1,1"));

        MapCell mapCell = dataLoadingService.readMapCellLine(" 1 , 2 , 3.0 ");
        Assert.assertNotNull(mapCell);
        Assert.assertEquals(mapCell.getId().getTile_x(), new Integer(1));
        Assert.assertEquals(mapCell.getId().getTile_y(), new Integer(2));
        Assert.assertEquals(mapCell.getMean_distance(), new Double(3.0));
    }

    @Test(expected = ServiceException.class)
    public void testGetLocalityNoUser() throws Exception {
        localityService.getLocality(1L, 2.0, 3.0);
    }

    @Test(expected = ServiceException.class)
    public void testGetLocalityNoMapCell() throws Exception {
        Placemark placemark = new Placemark();
        placemark.setUserId(1L);
        placemark.setLat(2.0);
        placemark.setLon(3.0);
        sessionFactory.getCurrentSession().saveOrUpdate(placemark);

        localityService.getLocality(1L, 2.0, 3.0);
    }

    @Test
    public void testGetLocality() throws Exception {
        Long userId = 1L;
        Double lat = 2.0;
        Double lon = 3.0;
        Session session = sessionFactory.getCurrentSession();

        Placemark placemark = new Placemark();
        placemark.setUserId(userId);
        placemark.setLat(lat);
        placemark.setLon(lon);
        session.saveOrUpdate(placemark);

        MapCell mapCell = new MapCell();
        mapCell.setId(new MapCellPK(lon.intValue(), lat.intValue()));
        mapCell.setMean_distance(3.0);
        session.saveOrUpdate(mapCell);
        session.flush();

        LocalityDTO localityDTO = localityService.getLocality(userId, lat, lon);
        Assert.assertEquals(localityDTO.getNear_the_placemark(), Boolean.TRUE);
        Assert.assertEquals(localityDTO.getDistance(), 0.0);
        Assert.assertEquals(localityDTO.getMean_distance(), 3.0);

        MapCell zeroMapCell = new MapCell();
        zeroMapCell.setId(new MapCellPK(0, 0));
        zeroMapCell.setMean_distance(0.0);
        session.saveOrUpdate(zeroMapCell);
        session.flush();

        localityDTO = localityService.getLocality(userId, 0.0, 0.0);
        Assert.assertEquals(localityDTO.getNear_the_placemark(), Boolean.FALSE);
        Assert.assertEquals(localityDTO.getDistance(), 400.86262447368404);
        Assert.assertEquals(localityDTO.getMean_distance(), 0.0);
    }

    @Test
    public void testPutPlacemark() throws Exception {
        NewPlacemarkDTO newPlacemarkDTO = new NewPlacemarkDTO();
        newPlacemarkDTO.setLat(2.0);
        newPlacemarkDTO.setLon(3.0);

        Placemark placemark = localityService.putPlacemark(1L, newPlacemarkDTO);
        Assert.assertEquals(placemark.getUserId(), new Long(1));
        Assert.assertEquals(placemark.getLat(), new Double(2.0));
        Assert.assertEquals(placemark.getLon(), new Double(3.0));
    }

    @Test
    public void testGetUsersCellStats() throws Exception {
        Double lat = 1.23;
        Double lon = 4.56;

        UsersCellStatsDTO stats = localityService.getUsersCellStats(lat, lon);
        Assert.assertEquals(stats.getCount(), new Long(0));

        NewPlacemarkDTO newPlacemarkDTO = new NewPlacemarkDTO();
        newPlacemarkDTO.setLat(lat);
        newPlacemarkDTO.setLon(lon);
        localityService.putPlacemark(1L, newPlacemarkDTO);
        stats = localityService.getUsersCellStats(lat, lon);
        Assert.assertEquals(stats.getCount(), new Long(1));

        localityService.putPlacemark(2L, newPlacemarkDTO);
        stats = localityService.getUsersCellStats(lat, lon);
        Assert.assertEquals(stats.getCount(), new Long(2));
    }
}
