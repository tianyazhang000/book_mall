package com.test;

import com.dao.BookDao;
import com.dao.impl.BookDaoImpl;
import com.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BookDaoTest {

    private BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null,"钢铁是怎样炼成的","尼古拉·奥斯特洛夫斯基",new BigDecimal(39),29,71,null));

    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(19);
    }

    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(13,"西游记","吴承恩",new BigDecimal(12),19,81,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(13));
    }

    @Test
    public void queryBooks() {
        for (Book queryBook : bookDao.queryBooks()) {
            System.out.println(queryBook);
        }
    }
}