package com.onefactor.dao;

import com.onefactor.dmodel.Placemark;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 23:45
 */
public class PlacemarkJdbcDao implements PlacemarkDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Placemark find(Long id) {
        return (Placemark) sessionFactory.getCurrentSession().get(Placemark.class, id);
    }

    @Override
    public void store(Placemark placemark) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(placemark);
        session.flush();
    }
}
