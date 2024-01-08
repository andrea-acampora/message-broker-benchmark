import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TestHelloWorld : StringSpec({


    "Sample test" {
        "Hello World!" shouldBe "Hello World!"
    }
})
