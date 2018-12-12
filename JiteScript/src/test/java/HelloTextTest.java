import me.qmx.jitescript.JDKVersion;
import me.qmx.jitescript.JiteClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;

import static me.qmx.jitescript.CodeBlock.newCodeBlock;
import static me.qmx.jitescript.util.CodegenUtils.p;
import static me.qmx.jitescript.util.CodegenUtils.sig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by CodeCouple.pl
 */
@ExtendWith(MockitoExtension.class)
class HelloTextTest {

    private CustomClassLoader customClassLoader = new CustomClassLoader();

    @Mock
    private Logger logger;

    @Test
    void shouldReturnCustomText() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        // Given
        JiteClass helloText = new JiteClass("HelloText");
        createConstructor(helloText);

        byte[] bytes = helloText.toBytes(JDKVersion.V1_8);
        Class<?> clazz = customClassLoader.defineClass("HelloText", bytes);

        // When
        Object newInstance = clazz.getConstructors()[0].newInstance(logger, "ABC");

        // Then
        assertThat(newInstance).isNotNull();
        verify(logger).log("ABC");
    }

    private void createConstructor(final JiteClass helloText) {
        helloText.defineMethod("<init>", JiteClass.ACC_PUBLIC, sig(Void.TYPE, Logger.class, String.class),
                newCodeBlock()
                        .aload(0)
                        .invokespecial(p(Object.class), "<init>", sig(void.class))
                        .aload(1)
                        .aload(2)
                        .invokevirtual(p(Logger.class), "log", sig(void.class, String.class))
                        .voidreturn());
    }

}
