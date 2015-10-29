package com.savoirtech.itests;

import org.apache.karaf.features.FeaturesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.KarafDistributionConfigurationFilePutOption;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;

import javax.inject.Inject;
import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;


@ExamReactorStrategy(PerClass.class)
@RunWith(PaxExam.class)
public class FSTOiTest {

    @Inject
    protected FeaturesService featuresService;

    @Inject
    protected BundleContext bundleContext;


    @Configuration
    public static Option[] configuration() throws Exception {
        return new Option[] {
                karafDistributionConfiguration().frameworkUrl(maven().groupId("org.apache.karaf").artifactId("apache-karaf")
                        .type("tar.gz")).karafVersion("4.0.2")
                        .unpackDirectory(new File("target/exam"))
                        .useDeployFolder(false),
                //configureConsole().ignoreLocalConsole(),
                logLevel(LogLevel.INFO),
                keepRuntimeFolder(),
                junitBundles(),
                new KarafDistributionConfigurationFilePutOption("etc/org.apache.karaf.management.cfg", "rmiRegistryHost", "127.0.0.1"),
                new KarafDistributionConfigurationFilePutOption("etc/org.apache.karaf.management.cfg", "rmiServerPort", "21413"),
                new KarafDistributionConfigurationFilePutOption("etc/org.apache.karaf.management.cfg", "rmiServerHost", "127.0.0.1"),
                new KarafDistributionConfigurationFilePutOption("etc/org.apache.karaf.shell.cfg", "sshHost", "127.0.0.1"),
                features(
                        maven().groupId("org.apache.karaf.features")
                                .artifactId("standard").type("xml")
                                .classifier("features").version("4.0.2"),
                        "jndi"
                )
        };
    }

    @Test
    public void ensureContainerStartup() {
        assertNotNull(bundleContext);
    }


    @Test
    public void ntpCoreTest() {
        try {
            featuresService.installFeature("fsto-deps");
            featuresService.installFeature("fsto-application");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}

