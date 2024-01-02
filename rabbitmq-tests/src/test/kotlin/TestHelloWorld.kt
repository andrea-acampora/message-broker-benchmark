import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestHelloWorld : StringSpec({

    val hello = HelloWorld()

    "Sample test" {
        hello.world shouldBe "Hello World!"
    }
})
