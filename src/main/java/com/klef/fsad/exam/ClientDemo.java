package com.klef.fsad.exam;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ClientDemo
{
    public static void main(String[] args) throws ParseException
    {
        Configuration cfg = new Configuration();
        cfg.configure();
        cfg.addAnnotatedClass(Delivery.class);

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Delivery d1 = new Delivery(1, "Akhil", sdf.parse("2026-05-02"), "Booked", "Hyderabad", "Vijayawada", 2.5);
        Delivery d2 = new Delivery(2, "Meera", sdf.parse("2026-05-03"), "In Transit", "Chennai", "Bengaluru", 4.2);

        session.persist(d1);
        session.persist(d2);

        tx.commit();

        Transaction tx2 = session.beginTransaction();

        Query<?> q = session.createQuery(
        "delete from Delivery where id=?1");

        q.setParameter(1, 1);

        int n = q.executeUpdate();

        System.out.println("Records Deleted = "+n);

        tx2.commit();

        session.close();
        sf.close();
    }
}