package com.Controller;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.entity.StudentEntity;
import com.util.HibernateUtil;

public class Controller {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Insert Student");
          System.out.println("2. View All Students");
          System.out.println("3. Update Student");
          System.out.println("4. Delete Student");
            System.out.println("5. Exit");
           System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    insertStudent(sc);
                    break;
                case 2:
                    getAllStudents();
                    break;
                case 3:
                    updateStudent(sc);
                    break;
                case 4:
                    deleteStudent(sc);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
        sc.close();
    }

    public static void insertStudent(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        StudentEntity student = new StudentEntity();
        student.setSid(id);
        student.setSname(name);
        student.setAddress(address);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            System.out.println("Student inserted successfully.");
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Insert failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }
//main function 
    public static void getAllStudents() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            Query<StudentEntity> query = session.createQuery("FROM com.entity.StudentEntity", StudentEntity.class);
            List<StudentEntity> list = query.getResultList();
            System.out.println("\n--- Student Records ---");
            for (StudentEntity s : list) {
                System.out.println("ID: " + s.getSid() + ", Name: " + s.getSname() + ", Address: " + s.getAddress());
            }
        } finally {
            session.close();
        }
    }

    public static void updateStudent(Scanner sc) {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Address: ");
        String address = sc.nextLine();

        StudentEntity student = new StudentEntity();
        student.setSid(id);
        student.setSname(name);
        student.setAddress(address);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
            System.out.println("Student updated successfully.");
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Update failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void deleteStudent(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            StudentEntity student = session.get(StudentEntity.class, id);
            if (student != null) {
                session.delete(student);
                session.getTransaction().commit();
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student not found.");
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Delete failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
