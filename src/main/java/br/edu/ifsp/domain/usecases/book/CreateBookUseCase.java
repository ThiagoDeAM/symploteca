package br.edu.ifsp.domain.usecases.book;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class CreateBookUseCase {

    private BookDAO bookDAO;

    public CreateBookUseCase(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public Integer insert(Book book){
        Validator<Book> validator = new BookInputRequestValidator();
        Notification notification = validator.validate(book);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        if (book.getStatus() != BookStatus.AVAILABLE)
            throw new IllegalArgumentException("New books must be created as AVAILABLE");

        String isbn = book.getIsbn();
        if(bookDAO.findByIsnb(isbn).isPresent())
            throw new EntityAlreadyExistsException("This ISBN is already in use.");

        return bookDAO.create(book);
    }
}
