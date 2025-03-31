package br.edu.ifsp.domain.usecases.book;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class BookInputRequestValidator extends Validator<Book> {
    @Override
    public Notification validate(Book book) {
        Notification notification = new Notification();

        if (book == null) {
            notification.addError("Book is null");
            return notification;
        }
        if(nullOrEmpty(book.getName()))
            notification.addError("Name is null or empty");
        if(nullOrEmpty(book.getIsbn()))
            notification.addError("ISBN is null or empty");
        if(nullOrEmpty(book.getAuthors()))
            notification.addError("Authors are null or empty");
        if(nullOrEmpty(book.getPublisher()))
            notification.addError("Publisher is null or empty");

        if (book.getEdition() == null)
            notification.addError("Edition is null");
        if (book.getNumberOfPages() == null || book.getNumberOfPages() <= 0)
            notification.addError("Number of pages is null or <= 0");
        if (book.getGender() == null)
            notification.addError("Book gender is null");



        return notification;
    }
}
