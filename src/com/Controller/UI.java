package com.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.entity.StudentEntity;
import com.util.HibernateUtil;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UI {
    private JFrame frame;
    private JTextField idField, nameField, addressField;
    private DefaultTableModel tableModel;
    private JTable table;

    public UI() {
        frame = new JFrame("StudentManagement");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Student Details"));

        inputPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        JButton addButton = new JButton("Add Student");
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Student ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");

        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        loadStudents();
        frame.setVisible(true);
    }

    private void addStudent() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String address = addressField.getText();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        StudentEntity student = new StudentEntity();
        student.setSid(id);
        student.setSname(name);
        student.setAddress(address);

        session.save(student);
        tx.commit();
        session.close();

        loadStudents();
    }

    private void loadStudents() {
        tableModel.setRowCount(0);

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<StudentEntity> students = session.createQuery("from com.entity.StudentEntity", StudentEntity.class).list();
        session.close();

        for (StudentEntity stu : students) {
            tableModel.addRow(new Object[]{stu.getSid(), stu.getSname(), stu.getAddress()});
        }
    }

    public static void main(String[] args) {
        new UI();
    }
}
