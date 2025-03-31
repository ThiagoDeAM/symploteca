package br.edu.ifsp.domain.usecases.book;

import br.edu.ifsp.domain.entities.book.Book;
import br.edu.ifsp.domain.entities.book.BookGender;
import br.edu.ifsp.domain.entities.book.BookStatus;
import br.edu.ifsp.domain.usecases.utils.EntityAlreadyExistsException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBookUseCaseTest {

    @Mock
    BookDAO bookDaoMock;
    @InjectMocks
    CreateBookUseCase sut;
    Book book;

    @BeforeEach
    void setup(){
        book = new Book(1, 100, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);
    }

    @Nested
    @DisplayName("For valid tests: ")
    class ValidTests{
        @Test
        @Tag("UnitTest")
        @DisplayName("Should create book when valid")
        void shouldCreateBookWhenValid(){
            //Given
            book = new Book(1, 100, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            verify(bookDaoMock).create(book);

        }

        @ParameterizedTest(name = "[{index}] Book gender: {0}")
        @EnumSource(BookGender.class)
        /*@CsvSource({
                "ACTION",
                "DRAMA",
                "HISTORY",
                "HORROR",
                "SCIENCE",
                "TECHNICAL"
        })*/
        @DisplayName("Should insert a book when book gender is valid")
        void shouldInsertABookWhenBookGenderIsValid(BookGender bookGender){
            //Given
            book = new Book(1, 100, "Livro", "Autor", "Editora", "1234567890", bookGender, BookStatus.AVAILABLE);

            //Mock
            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            //Verify
            verify(bookDaoMock).create(book);

        }

    }
    @Nested
    @DisplayName("For invalid tests: ")
    class InvalidTests{
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when edition is null")
        void shouldThrowExceptionWhenEditionIsNull(){
            //Given
            book = new Book(null, 100, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Edition is null");
            /*
            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            verify(bookDaoMock).create(book);*/
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when number of pages is null")
        void shouldThrowExceptionWhenNumberOfPagesIsNull(){
            //Given
            book = new Book(1, null, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Number of pages is null or <= 0");

            /*
            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            verify(bookDaoMock).create(book);*/
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when number of pages is negative")
        void shouldThrowExceptionWhenNumberOfPagesIsNegative(){
            //Given
            book = new Book(1, -1, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Number of pages is null or <= 0");

            /*
            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            verify(bookDaoMock).create(book);*/
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when number of pages is zero")
        void shouldThrowExceptionWhenNumberOfPagesIsZero(){
            //Given
            book = new Book(1, 0, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Number of pages is null or <= 0");
           /*
            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            verify(bookDaoMock).create(book);*/
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when book when book gender is null")
        void shouldThrowExceptionWhenBookGenderIsNull(){
            //Given
            book = new Book(1, 100, "Livro", "Autor", "Editora", "1234567890", null, BookStatus.AVAILABLE);

            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Book gender is null");

            /*
            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            verify(bookDaoMock).create(book);*/
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when book when book status is null")
        void shouldThrowExceptionWhenBookStatusIsNull(){
            //Given
            book = new Book(1, 100, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, null);

            assertThatThrownBy(()-> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("New books must be created as AVAILABLE");
            /*
            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            when(bookDaoMock.create(book)).thenReturn(1);

            //When
            Integer result = sut.insert(book);

            //Then
            assertThat(result).isEqualTo(1);

            verify(bookDaoMock).create(book);*/
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when book is null")
        void shouldThrowExceptionWhenBookIsNull(){
            //Given
            book = null;

            //When /Then
            assertThatThrownBy(()-> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Book is null");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when book name is null")
        void shouldThrowExceptionWhenBookNameIsNull(){
            //Given
            book = new Book(1, 100, null, "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Name is null or empty");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when book name is empty")
        void shouldThrowExceptionWhenBookNameIsEmpty(){
            //Given
            book = new Book(1, 100, "", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Name is null or empty");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when isbn is null")
        void shouldThrowExceptionWhenIsbnIsNull(){
            //Given
            book = new Book(1, 100, "Nome", "Autor", "Editora", null, BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("ISBN is null or empty");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when isbn is empty")
        void shouldThrowExceptionWhenIsbnIsEmpty(){
            //Given
            book = new Book(1, 100, "Nome", "Autor", "Editora", "", BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("ISBN is null or empty");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when isbn already exists")
        void shouldThrowExceptionWhenIsbnAlreadyExists(){
            //Given
            book = new Book(1, 100, "Nome", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.of(book));

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(EntityAlreadyExistsException.class)
                    .hasMessageContaining("ISBN");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when author is null")
        void shouldThrowExceptionWhenAuthorIsNull(){
            //Given
            book = new Book(1, 100, "Nome", null, "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Authors are null or empty");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when author is empty")
        void shouldThrowExceptionWhenAuthorIsEmpty(){
            //Given
            book = new Book(1, 100, "Nome", "", "Editora", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Authors are null or empty");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when book status is checked out")
        void shouldThrowExceptionWhenBookStatusIsCheckedOut(){
            //Given
            book = new Book(1, 100, "Livro", "Autor", "Editora", "1234567890", BookGender.ACTION, BookStatus.CHECKED_OUT);

            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("New books must be created as AVAILABLE");

            //when(bookDaoMock.findByIsnb("1234567890")).thenReturn(Optional.empty());
            //when(bookDaoMock.create(book)).thenReturn(1);

            //When
            //Integer result = sut.insert(book);

            //Then
            //assertThat(result).isEqualTo(1);

            //verify(bookDaoMock).create(book);
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when publisher is null")
        void shouldThrowExceptionWhenPublisherIsNull(){
            //Given
            book = new Book(1, 100, "Nome", "Autor", null, "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Publisher is null or empty");
        }
        @Test
        @Tag("UnitTest")
        @DisplayName("Should throw Exception when publisher is empty")
        void shouldThrowExceptionWhenPublisherIsEmpty(){
            //Given
            book = new Book(1, 100, "Nome", "Autor", "", "1234567890", BookGender.ACTION, BookStatus.AVAILABLE);

            //When /Then
            assertThatThrownBy(() -> sut.insert(book))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Publisher is null or empty");
        }
    }
  
}