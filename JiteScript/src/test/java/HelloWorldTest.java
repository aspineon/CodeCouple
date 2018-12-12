import me.qmx.jitescript.CodeBlock;
import me.qmx.jitescript.JDKVersion;
import me.qmx.jitescript.JiteClass;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static me.qmx.jitescript.util.CodegenUtils.ci;
import static me.qmx.jitescript.util.CodegenUtils.p;
import static me.qmx.jitescript.util.CodegenUtils.sig;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class HelloWorldTest {

    private CustomClassLoader customClassLoader = new CustomClassLoader();

    @Test
    void shouldReturnHelloWorld() throws IllegalAccessException, InstantiationException {
        // Given
        JiteClass helloWorld = new JiteClass("HelloWorld");
        helloWorld.defineMethod("<init>", JiteClass.ACC_PUBLIC, sig(Void.TYPE), createConstructor());

        byte[] bytes = helloWorld.toBytes(JDKVersion.V1_8);
        Class<?> clazz = customClassLoader.defineClass("HelloWorld", bytes);

        // When
        Object newInstance = clazz.newInstance();

        // Then
        assertThat(newInstance).isNotNull();
    }

    private CodeBlock createConstructor() {
        CodeBlock constructor = new CodeBlock();
        constructor.aload(0);
        constructor.invokespecial(p(Object.class), "<init>", sig(void.class));
        constructor.getstatic(p(System.class), "out", ci(PrintStream.class));
        constructor.ldc("HelloWorld");
        constructor.invokevirtual(p(PrintStream.class), "println", sig(void.class, Object.class));
        constructor.voidreturn();
        return constructor;
    }

}
