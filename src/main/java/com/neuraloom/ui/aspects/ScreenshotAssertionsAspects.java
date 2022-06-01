package com.neuraloom.ui.aspects;

import com.neuraloom.ui.screenshot.AttachmentBuilder;
import com.neuraloom.ui.screenshot.exceptions.NoReferenceScreenshotException;
import com.neuraloom.ui.screenshot.exceptions.ScreenshotDiffException;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import static io.qameta.allure.util.ResultsUtils.getStatus;
import static io.qameta.allure.util.ResultsUtils.getStatusDetails;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;

@Aspect
public class ScreenshotAssertionsAspects {
    private static final InheritableThreadLocal<AllureLifecycle> LIFECYCLE = new InheritableThreadLocal<>() {
        @Override
        protected AllureLifecycle initialValue() {
            return Allure.getLifecycle();
        }
    };

    public static AllureLifecycle getLifecycle() {
        return LIFECYCLE.get();
    }

    private static String toBase64String(BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = stream.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Pointcut("execution(public void com.neuraloom.ui.screenshot.ScreenShooter.save(..))")
    public void savePointcut() {
    }

    @Pointcut("execution(* com.neuraloom.ui.screenshot.ScreenShooter.comparisonPassed(..))")
    public void noDiffPointcut() {
    }

    @Pointcut("execution(* com.neuraloom.ui.screenshot.ScreenShooter.comparisonFailed(..))")
    public void hasDiffPointcut() {
    }

    @Around("savePointcut()")
    public Object noReferenceStep(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = (String) joinPoint.getArgs()[0];
        String uuid = UUID.randomUUID().toString();
        getLifecycle().startStep(uuid, new StepResult().setName(format("No reference screenshot [%s] for comparison:", name)));
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof NoReferenceScreenshotException) {
                Screenshot actual = (Screenshot) joinPoint.getArgs()[1];
                String body = new AttachmentBuilder()
                        .withId("NL-" + uuid)
                        .withActual(toBase64String(actual.getImage()))
                        .build();
                getLifecycle().updateStep(uuid, s -> s.setStatus(Status.FAILED));
                getLifecycle().addAttachment("Actual", "text/html", ".html", body.getBytes(UTF_8));
            } else {
                getLifecycle().updateStep(uuid, s -> s.setStatus(getStatus(throwable).orElse(Status.BROKEN))
                        .setStatusDetails(getStatusDetails(throwable).orElse(null)));
            }
            throw throwable;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }

    @Around("noDiffPointcut()")
    public Object noDiffStep(ProceedingJoinPoint joinPoint) throws Throwable {
        String uuid = UUID.randomUUID().toString();
        getLifecycle().startStep(uuid, new StepResult().setName("Screenshot comparison passed"));
        try {
            Object proceed = joinPoint.proceed();
            getLifecycle().updateStep(uuid, s -> s.setStatus(Status.PASSED));
            return proceed;
        } catch (Throwable throwable) {
            getLifecycle().updateStep(uuid, s -> s.setStatus(getStatus(throwable).orElse(Status.BROKEN))
                    .setStatusDetails(getStatusDetails(throwable).orElse(null)));
            throw throwable;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }

    @Around("hasDiffPointcut()")
    public Object hasDiffStep(ProceedingJoinPoint joinPoint) throws Throwable {
        String uuid = UUID.randomUUID().toString();
        getLifecycle().startStep(uuid, new StepResult().setName("Screenshot comparison failed:"));
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof ScreenshotDiffException) {
                ImageDiff diff = (ImageDiff) joinPoint.getArgs()[0];
                Screenshot reference = (Screenshot) joinPoint.getArgs()[1];
                Screenshot actual = (Screenshot) joinPoint.getArgs()[2];
                String body = new AttachmentBuilder()
                        .withId("NL-" + uuid)
                        .withActual(toBase64String(actual.getImage()))
                        .withReference(toBase64String(reference.getImage()))
                        .withDiff(toBase64String(diff.getMarkedImage()))
                        .withDiffSize(valueOf(diff.getDiffSize()))
                        .build();
                getLifecycle().updateStep(uuid, s -> s.setStatus(Status.FAILED));
                getLifecycle().addAttachment("Actual", "text/html", ".html", body.getBytes(UTF_8));
            } else {
                getLifecycle().updateStep(uuid, s -> s.setStatus(getStatus(throwable).orElse(Status.BROKEN))
                        .setStatusDetails(getStatusDetails(throwable).orElse(null)));
            }
            throw throwable;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }
}
