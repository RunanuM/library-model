/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.gui;

/**
 *
 * @author Emperor_RR
 */


import com.library.dao.BookDAO;
import com.library.model.Book;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookGui extends JFrame {
    private BookDAO bookDAO = new BookDAO();
    private JTable bookTable;
    private DefaultTableModel tableModel;

    // Form Fields
    private JTextField txtTitle, txtAuthor, txtIsbn, txtYear, txtGenre, txtSearch;
    
    private Font bigFont = new Font("SansSerif", Font.PLAIN, 24); 

    public BookGui() {
        setTitle("Emperor_RR Library System");
        setSize(1200, 900); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));

        // TOP: Input Form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Book Details", 0, 0, bigFont));

        // Create and Add Components with Big Font
        formPanel.add(createBigLabel("Title:"));
        txtTitle = createBigTextField(); formPanel.add(txtTitle);
        
        formPanel.add(createBigLabel("Author:"));
        txtAuthor = createBigTextField(); formPanel.add(txtAuthor);
        
        formPanel.add(createBigLabel("ISBN:"));
        txtIsbn = createBigTextField(); formPanel.add(txtIsbn);
        
        formPanel.add(createBigLabel("Publish Year:"));
        txtYear = createBigTextField(); formPanel.add(txtYear);
        
        formPanel.add(createBigLabel("Genre:"));
        txtGenre = createBigTextField(); formPanel.add(txtGenre);

        // --- Buttons ---
        JButton btnAdd = createBigButton("Add Book", Color.GREEN);
        JButton btnUpdate = createBigButton("Update Selected", Color.ORANGE);
        JButton btnDelete = createBigButton("Delete Selected", Color.RED);
        
        formPanel.add(btnAdd);
        formPanel.add(btnUpdate);

        add(formPanel, BorderLayout.NORTH);

        //CENTER: Table & Search
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        // Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        searchPanel.add(createBigLabel("Search Title:"));
        txtSearch = createBigTextField();
        txtSearch.setColumns(15);
        JButton btnSearch = createBigButton("Filter", Color.LIGHT_GRAY);
        
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnDelete); 
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // Table Customization
        String[] columns = {"Title", "Author", "ISBN", "Year", "Genre"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        
        //Set Table Font and Row Height
        bookTable.setFont(bigFont);
        bookTable.setRowHeight(40); // Make rows tall enough for big text
        bookTable.getTableHeader().setFont(bigFont.deriveFont(Font.BOLD));
        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        setupLogic(btnAdd, btnSearch, btnUpdate, btnDelete);

        refreshTable();
    }

    private JLabel createBigLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(bigFont);
        return label;
    }

    private JTextField createBigTextField() {
        JTextField field = new JTextField();
        field.setFont(bigFont);
        return field;
    }

    private JButton createBigButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(bigFont.deriveFont(Font.BOLD));
        return btn;
    }

    private void setupLogic(JButton btnAdd, JButton btnSearch, JButton btnUpdate, JButton btnDelete) {
        btnAdd.addActionListener(e -> {
            if(validateFields()) {
                Book book = new Book(txtTitle.getText(), txtAuthor.getText(), 
                                     txtIsbn.getText(), Integer.parseInt(txtYear.getText()), 
                                     txtGenre.getText());
                bookDAO.addBook(book);
                refreshTable();
                clearFields();
            }
        });

        btnSearch.addActionListener(e -> filterTable(txtSearch.getText()));

        btnUpdate.addActionListener(e -> {
            int row = bookTable.getSelectedRow();
            if (row != -1) {
                Book book = new Book(txtTitle.getText(), txtAuthor.getText(), 
                                     txtIsbn.getText(), Integer.parseInt(txtYear.getText()), 
                                     txtGenre.getText());
                bookDAO.updateBook(book);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select a book first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int row = bookTable.getSelectedRow();
            if (row != -1) {
                String isbn = tableModel.getValueAt(row, 2).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this book?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookDAO.deleteBook(isbn);
                    refreshTable();
                }
            }
        });

        bookTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && bookTable.getSelectedRow() != -1) {
                int row = bookTable.getSelectedRow();
                txtTitle.setText(tableModel.getValueAt(row, 0).toString());
                txtAuthor.setText(tableModel.getValueAt(row, 1).toString());
                txtIsbn.setText(tableModel.getValueAt(row, 2).toString());
                txtYear.setText(tableModel.getValueAt(row, 3).toString());
                txtGenre.setText(tableModel.getValueAt(row, 4).toString());
                txtIsbn.setEditable(false);
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.findAll();
        for (Book b : books) {
            tableModel.addRow(new Object[]{b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublishYear(), b.getGenre()});
        }
        txtIsbn.setEditable(true);
    }

    private void filterTable(String keyword) {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.findAll();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                tableModel.addRow(new Object[]{b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublishYear(), b.getGenre()});
            }
        }
    }

    private boolean validateFields() {
        try {
            if (txtTitle.getText().isEmpty() || txtIsbn.getText().isEmpty()) throw new Exception();
            Integer.parseInt(txtYear.getText());
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Check fields! Year must be a number.");
            return false;
        }
    }

    private void clearFields() {
        txtTitle.setText(""); txtAuthor.setText(""); txtIsbn.setText(""); 
        txtYear.setText(""); txtGenre.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookGui().setVisible(true));
    }
}