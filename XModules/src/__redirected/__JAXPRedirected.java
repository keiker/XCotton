/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package __redirected;

import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;

/**
 * Executes common operations against ALL JAXP redirection classes.
 *
 * @author Jason T. Greene
 */
public class __JAXPRedirected {

    /**
     * Change all provided factories to the ones contained in the
     * specified module using the standard META-INF/services lookup
     * pattern.
     *
     * @param id the id for the jaxp module
     * @param loader the loader containing the jaxp module
     */
    public static void changeAll(ModuleIdentifier id, ModuleLoader loader) {
        __DocumentBuilderFactory.changeDefaultFactory(id, loader);
        __SAXParserFactory.changeDefaultFactory(id, loader);
        __TransformerFactory.changeDefaultFactory(id, loader);
        __XPathFactory.changeDefaultFactory(id, loader);
        __XMLEventFactory.changeDefaultFactory(id, loader);
        __XMLInputFactory.changeDefaultFactory(id, loader);
        __XMLOutputFactory.changeDefaultFactory(id, loader);
        __DatatypeFactory.changeDefaultFactory(id, loader);
        __SchemaFactory.changeDefaultFactory(id, loader);
        __XMLReaderFactory.changeDefaultFactory(id, loader);
    }

    /**
     * Restores all JAXP factories to the ones contained in the JDK
     * system classpath.
     */
    public static void restorePlatformFactory() {
        __DocumentBuilderFactory.restorePlatformFactory();
        __SAXParserFactory.restorePlatformFactory();
        __TransformerFactory.restorePlatformFactory();
        __XPathFactory.restorePlatformFactory();
        __XMLEventFactory.restorePlatformFactory();
        __XMLInputFactory.restorePlatformFactory();
        __XMLOutputFactory.restorePlatformFactory();
        __DatatypeFactory.restorePlatformFactory();
        __SchemaFactory.restorePlatformFactory();
        __XMLReaderFactory.restorePlatformFactory();
    }

    /**
     * Initializes the JAXP redirection system.
     */
    public static void initAll() {
        __DocumentBuilderFactory.init();
        __SAXParserFactory.init();
        __TransformerFactory.init();
        __XPathFactory.init();
        __XMLEventFactory.init();
        __XMLInputFactory.init();
        __XMLOutputFactory.init();
        __DatatypeFactory.init();
        __SchemaFactory.init();
        __XMLReaderFactory.init();
    }
}
