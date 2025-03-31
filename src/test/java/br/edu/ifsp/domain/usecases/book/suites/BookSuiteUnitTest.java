package br.edu.ifsp.domain.usecases.book.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("br.edu.ifsp.domain.entities.book")
@IncludeTags({"UnitTest"})
@SuiteDisplayName("All unit tests")
public class BookSuiteUnitTest {
}
