/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999,2000 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package org.apache.xerces.parsers;

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLParserConfiguration;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * This class sets the defaults for the following features and properties:
 * <ul>
 * <li>http://xml.org/sax/features/validation</li>
 * <li>http://xml.org/sax/features/namespaces</li>
 * <li>http://xml.org/sax/features/external-general-entities</li>
 * <li>http://xml.org/sax/features/external-parameter-entities</li>
 * <li>http://apache.org/xml/features/validation/warn-on-duplicate-attdef</li>
 * <li>http://apache.org/xml/features/validation/warn-on-undeclared-elemdef</li>
 * <li>http://apache.org/xml/features/allow-java-encodings</li>
 * <li>http://apache.org/xml/features/continue-after-fatal-error</li>
 * <li>http://xml.org/sax/properties/xml-string</li>
 * <li>http://apache.org/xml/properties/internal/symbol-table</li>
 * <li>http://apache.org/xml/properties/internal/error-reporter</li>
 * <li>http://apache.org/xml/properties/internal/error-handler</li>
 * <li>http://apache.org/xml/properties/internal/entity-manager</li>
 * <li>http://apache.org/xml/properties/internal/entity-resolver</li>
 * <li>http://apache.org/xml/properties/internal/grammar-pool</li>
 * <li>http://apache.org/xml/properties/internal/datatype-validator-factory</li>
 * </ul>
 *
 * @author Arnaud  Le Hors, IBM
 * @author Andy Clark, IBM
 *
 * @version $Id$
 */
public abstract class BasicParserConfiguration
    implements XMLParserConfiguration {

    //
    // Data
    //

    // components (non-configurable)

    /** Symbol table. */
    protected SymbolTable fSymbolTable;

    /** Locator */
    protected Locator fLocator;

    // components (configurable)

    /** Entity manager. */
    protected XMLEntityManager fEntityManager;

    /** Error reporter. */
    protected XMLErrorReporter fErrorReporter;

    // data

    /** Properties. */
    protected Hashtable fProperties;

    /** Features. */
    protected Hashtable fFeatures;

    /** Components. */
    protected Vector fComponents;

    protected boolean fNeedInitialize;

    protected XMLDocumentHandler fDocumentHandler;

    protected XMLDTDHandler fDTDHandler;

    protected XMLDTDContentModelHandler fDTDContentModelHandler;

    // constants

    static final String SYMBOL_TABLE = Constants.XERCES_PROPERTY_PREFIX +
        Constants.SYMBOL_TABLE_PROPERTY;
    static final String ENTITY_MANAGER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_MANAGER_PROPERTY;
    static final String ERROR_REPORTER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY;
    static final String LOCATOR =
        Constants.XERCES_PROPERTY_PREFIX + Constants.LOCATOR_PROPERTY;

    static final String VALIDATION =
        Constants.SAX_FEATURE_PREFIX + Constants.VALIDATION_FEATURE;
    static final String NAMESPACES =
        Constants.SAX_FEATURE_PREFIX + Constants.NAMESPACES_FEATURE;
    static final String EXTERNAL_GENERAL_ENTITIES =
        Constants.SAX_FEATURE_PREFIX +
        Constants.EXTERNAL_GENERAL_ENTITIES_FEATURE;
    static final String EXTERNAL_PARAMETER_ENTITIES =
        Constants.SAX_FEATURE_PREFIX +
        Constants.EXTERNAL_PARAMETER_ENTITIES_FEATURE;
    static final String WARN_ON_DUPLICATE_ATTDEF =
        Constants.XERCES_FEATURE_PREFIX +
        Constants.WARN_ON_DUPLICATE_ATTDEF_FEATURE;
    static final String WARN_ON_UNDECLARED_ELEMDEF =
        Constants.XERCES_FEATURE_PREFIX +
        Constants.WARN_ON_UNDECLARED_ELEMDEF_FEATURE;
    static final String ALLOW_JAVA_ENCODINGS =
        Constants.XERCES_FEATURE_PREFIX +
        Constants.ALLOW_JAVA_ENCODINGS_FEATURE;
    static final String CONTINUE_AFTER_FATAL_ERROR =
        Constants.XERCES_FEATURE_PREFIX +
        Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE;
    static final String LOAD_EXTERNAL_DTD =
        Constants.XERCES_FEATURE_PREFIX + Constants.LOAD_EXTERNAL_DTD_FEATURE;

    //
    // Constructors
    //

    /**
     * Default Constructor.
     */
    protected BasicParserConfiguration() {

        // create a vector to hold all the components in use
        fComponents = new Vector();

        // create table for features and properties
        fFeatures = new Hashtable();
        fProperties = new Hashtable();

        // set default values for features
        fFeatures.put(VALIDATION, Boolean.FALSE);
        fFeatures.put(NAMESPACES, Boolean.TRUE);
        fFeatures.put(EXTERNAL_GENERAL_ENTITIES, Boolean.TRUE);
        fFeatures.put(EXTERNAL_PARAMETER_ENTITIES, Boolean.TRUE);
        fFeatures.put(WARN_ON_DUPLICATE_ATTDEF, Boolean.FALSE);
        fFeatures.put(WARN_ON_UNDECLARED_ELEMDEF, Boolean.FALSE);
        fFeatures.put(ALLOW_JAVA_ENCODINGS, Boolean.FALSE);
        fFeatures.put(CONTINUE_AFTER_FATAL_ERROR, Boolean.FALSE);
        fFeatures.put(LOAD_EXTERNAL_DTD, Boolean.TRUE);

        fNeedInitialize = true;

    } // <init>

    /**
     * Constructs a document parser using the specified symbol table
     * and a default grammar pool.
     *
     */
    protected BasicParserConfiguration(SymbolTable symbolTable) {
        this();
        fSymbolTable = symbolTable;
        fProperties.put(SYMBOL_TABLE, fSymbolTable);
    } // <init>(SymbolTable)

    /**
     * Initialize the parser with all the components specified via the
     * properties plus any missing ones. This method MUST be called before
     * parsing. It is not called from the constructor though, so that the
     * application can pass in any components it wants by presetting the
     * relevant property.
     */
    protected void initialize() {

        // create and register missing components
        fSymbolTable = (SymbolTable) fProperties.get(SYMBOL_TABLE);
        if (fSymbolTable == null) {
            fSymbolTable = new SymbolTable();
            fProperties.put(SYMBOL_TABLE, fSymbolTable);
        }

        fEntityManager = (XMLEntityManager) fProperties.get(ENTITY_MANAGER);
        if (fEntityManager == null) {
            fEntityManager = createEntityManager();
            fProperties.put(ENTITY_MANAGER, fEntityManager);
        }
        fComponents.addElement(fEntityManager);

        fErrorReporter = (XMLErrorReporter) fProperties.get(ERROR_REPORTER);
        if (fErrorReporter == null) {
            fErrorReporter =
                createErrorReporter(fEntityManager.getEntityScanner());
            fProperties.put(ERROR_REPORTER, fErrorReporter);
        }
        fComponents.addElement(fErrorReporter);

        fLocator = (Locator) fEntityManager.getEntityScanner();
        fProperties.put(LOCATOR, fLocator);

        XMLMessageFormatter xmft = new XMLMessageFormatter();
        fErrorReporter.putMessageFormatter(XMLMessageFormatter.XML_DOMAIN, xmft);
        fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xmft);

        // set locale
        try {
            setLocale(Locale.getDefault());
        }
        catch (SAXException e) {
            // ignore
        }
        fNeedInitialize = false;

    } // initialize()

    //
    // Public methods
    //

    public void setDocumentHandler(XMLDocumentHandler handler) {
        fDocumentHandler = handler;
    }

    public void setDTDHandler(XMLDTDHandler handler) {
        fDTDHandler = handler;
    }

    public void setDTDContentModelHandler(XMLDTDContentModelHandler handler) {
        fDTDContentModelHandler = handler;
    }


    /**
     * Set the state of a feature.
     *
     * Set the state of any feature in a SAX2 parser.  The parser
     * might not recognize the feature, and if it does recognize
     * it, it might not be able to fulfill the request.
     *
     * @param featureId The unique identifier (URI) of the feature.
     * @param state The requested state of the feature (true or false).
     * @param check     Whether to check if the feature is recognized.
     *
     * @exception org.xml.sax.SAXNotRecognizedException If the
     *            requested feature is not known.
     * @exception org.xml.sax.SAXNotSupportedException If the
     *            requested feature is known, but the requested
     *            state is not supported.
     * @exception org.xml.sax.SAXException If there is any other
     *            problem fulfilling the request.
     */
    public void setFeature(String featureId, boolean state, boolean check)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (check) {
            checkFeature(featureId);
        }

        // forward to every component
        int count = fComponents.size();
        for (int i = 0; i < count; i++) {
            XMLComponent c = (XMLComponent) fComponents.elementAt(i);
            c.setFeature(featureId, state);
        }
        // then store the information
        fFeatures.put(featureId, state ? Boolean.TRUE : Boolean.FALSE);

    } // setFeature(String,boolean, boolean)

    /**
     * Sets the value of a property. This method is called by the parser
     * and gets propagated to components in this parser configuration.
     * 
     * @param propertyId The property identifier.
     * @param value      The value of the property.
     * @param check      Whether to check if the property is recognized.
     *
     * @throws SAXNotRecognizedException Thrown if the property is not
     *                                   recognized by this configuration
     *                                   or any of its components.
     * @throws SAXNotSupportedException Thrown if the value is not supported.
     */
    public void setProperty(String propertyId, Object value, boolean check)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (check) {
            checkProperty(propertyId);
        }

        // forward to every component
        int count = fComponents.size();
        for (int i = 0; i < count; i++) {
            XMLComponent c = (XMLComponent) fComponents.elementAt(i);
            c.setProperty(propertyId, value);
        }
        // then store the information
        fProperties.put(propertyId, value);

    } // setProperty(String,Object, boolean)

    /**
     * Returns the state of a feature.
     * 
     * @param featureId The feature identifier.
     * @param check     Whether to check if the feature is recognized.
     * 
     * @throws SAXNotRecognizedException Thrown if the feature is not 
     *                                   recognized.
     * @throws SAXNotSupportedException Thrown if the feature is not
     *                                  supported.
     */
    public boolean getFeature(String featureId, boolean check)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (check) {
            checkFeature(featureId);
        }
        Boolean state = (Boolean) fFeatures.get(featureId);
        return state.booleanValue();

    } // getFeature(String, boolean):boolean

    /**
     * Returns the value of a property.
     * 
     * @param propertyId The property identifier.
     * @param check      Whether to check if the property is recognized.
     * 
     * @throws SAXNotRecognizedException Thrown if the feature is not 
     *                                   recognized.
     * @throws SAXNotSupportedException Thrown if the feature is not
     *                                  supported.
     */
    public Object getProperty(String propertyId, boolean check)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (check) {
            checkProperty(propertyId);
        }
        return fProperties.get(propertyId);

    } // getProperty(String, boolean):Object

    /**
     * Set the locale to use for messages.
     *
     * @param locale The locale object to use for localization of messages.
     *
     * @exception SAXException An exception thrown if the parser does not
     *                         support the specified locale.
     *
     * @see org.xml.sax.Parser
     */
    public void setLocale(Locale locale) throws SAXException {

        fErrorReporter.setLocale(locale);

    } // setLocale(Locale)

    //
    // XMLComponentManager methods
    //

    /**
     * Returns the state of a feature.
     * 
     * @param featureId The feature identifier.
     * 
     * @throws SAXNotRecognizedException Thrown if the feature is not 
     *                                   recognized.
     * @throws SAXNotSupportedException Thrown if the feature is not
     *                                  supported.
     */
    public boolean getFeature(String featureId)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        return getFeature(featureId, true);

    } // getFeature(String):boolean

    /**
     * Returns the value of a property.
     * 
     * @param propertyId The property identifier.
     * 
     * @throws SAXNotRecognizedException Thrown if the feature is not 
     *                                   recognized.
     * @throws SAXNotSupportedException Thrown if the feature is not
     *                                  supported.
     */
    public Object getProperty(String propertyId)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        return getProperty(propertyId, true);

    } // getProperty(String):Object

    public Locator getLocator() {
        return fLocator;
    } // getLocator():Locator

    //
    // Protected methods
    //

    /**
     * reset all components before parsing
     */
    protected void reset() throws SAXException {

        // reset every component
        int count = fComponents.size();
        for (int i = 0; i < count; i++) {
            XMLComponent c = (XMLComponent) fComponents.elementAt(i);
            c.reset(this);
        }

    } // reset(XMLParser)

    /**
     * Check a feature. If feature is known and supported, this method simply
     * returns. Otherwise, the appropriate exception is thrown.
     *
     * @param featureId The unique identifier (URI) of the feature.
     *
     * @exception org.xml.sax.SAXNotRecognizedException If the
     *            requested feature is not known.
     * @exception org.xml.sax.SAXNotSupportedException If the
     *            requested feature is known, but the requested
     *            state is not supported.
     * @exception org.xml.sax.SAXException If there is any other
     *            problem fulfilling the request.
     */
    protected void checkFeature(String featureId)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        //
        // SAX2 Features
        //

        if (featureId.startsWith(Constants.SAX_FEATURE_PREFIX)) {
            String feature =
                featureId.substring(Constants.SAX_FEATURE_PREFIX.length());
            //
            // http://xml.org/sax/features/validation
            //   Validate (true) or don't validate (false).
            //
            if (feature.equals(Constants.VALIDATION_FEATURE)) {
                return;
            }
            //
            // http://xml.org/sax/features/namespaces
            //   Preprocess namespaces (true) or not (false).  See also
            //   the http://xml.org/sax/properties/namespace-sep property.
            //
            if (feature.equals(Constants.NAMESPACES_FEATURE)) {
                return;
            }
            //
            // http://xml.org/sax/features/external-general-entities
            //   Expand external general entities (true) or not (false).
            //
            if (feature.equals(Constants.EXTERNAL_GENERAL_ENTITIES_FEATURE)) {
                return;
            }
            //
            // http://xml.org/sax/features/external-parameter-entities
            //   Expand external parameter entities (true) or not (false).
            //
            if (feature.equals(Constants.EXTERNAL_PARAMETER_ENTITIES_FEATURE)) {
                return;
            }
            //
            // Not recognized
            //
        }

        //
        // Xerces Features
        //

        else if (featureId.startsWith(Constants.XERCES_FEATURE_PREFIX)) {
            String feature =
                featureId.substring(Constants.XERCES_FEATURE_PREFIX.length());
            //
            // http://apache.org/xml/features/validation/warn-on-duplicate-attdef
            //   Emits an error when an attribute is redefined.
            //
            if (feature.equals(Constants.WARN_ON_DUPLICATE_ATTDEF_FEATURE)) {
                return;
            }
            //
            // http://apache.org/xml/features/validation/warn-on-undeclared-elemdef
            //   Emits an error when an element's content model
            //   references an element, by name, that is not declared
            //   in the grammar.
            //
            if (feature.equals(Constants.WARN_ON_UNDECLARED_ELEMDEF_FEATURE)) {
                return;
            }
            //
            // http://apache.org/xml/features/allow-java-encodings
            //   Allows the use of Java encoding names in the XML
            //   and TextDecl lines.
            //
            if (feature.equals(Constants.ALLOW_JAVA_ENCODINGS_FEATURE)) {
                return;
            }
            //
            // http://apache.org/xml/features/continue-after-fatal-error
            //   Allows the parser to continue after a fatal error.
            //   Normally, a fatal error would stop the parse.
            //
            if (feature.equals(Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE)) {
                return;
            }
            //
            // Not recognized
            //
        }

        //
        // Not recognized
        //

        throw new SAXNotRecognizedException(featureId);

    } // checkFeature(String)

    /**
     * Check a property. If the property is known and supported, this method
     * simply returns. Otherwise, the appropriate exception is thrown.
     *
     * @param propertyId The unique identifier (URI) of the property
     *                   being set.
     * @exception org.xml.sax.SAXNotRecognizedException If the
     *            requested property is not known.
     * @exception org.xml.sax.SAXNotSupportedException If the
     *            requested property is known, but the requested
     *            value is not supported.
     * @exception org.xml.sax.SAXException If there is any other
     *            problem fulfilling the request.
     */
    protected void checkProperty(String propertyId)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        //
        // SAX2 Properties
        //

        if (propertyId.startsWith(Constants.SAX_PROPERTY_PREFIX)) {
            String property =
                propertyId.substring(Constants.SAX_PROPERTY_PREFIX.length());
            //
            // http://xml.org/sax/properties/xml-string
            // Value type: String
            // Access: read-only
            //   Get the literal string of characters associated with the
            //   current event.  If the parser recognises and supports this
            //   property but is not currently parsing text, it should return
            //   null (this is a good way to check for availability before the
            //   parse begins).
            //
            if (property.equals(Constants.XML_STRING_PROPERTY)) {
                // REVISIT - we should probably ask xml-dev for a precise
                // definition of what this is actually supposed to return, and
                // in exactly which circumstances.
                throw new SAXNotSupportedException(propertyId);
            }
        }

        //
        // Xerces Properties
        //

        else if (propertyId.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            String property = propertyId.substring(Constants.XERCES_PROPERTY_PREFIX.length());
            //
            // http://apache.org/xml/properties/internal/symbol-table
            //
            if (property.equals(Constants.SYMBOL_TABLE_PROPERTY)) {
                return;
            }
            //
            // http://apache.org/xml/properties/internal/error-reporter
            //
            if (property.equals(Constants.ERROR_REPORTER_PROPERTY)) {
                return;
            }
            //
            // http://apache.org/xml/properties/internal/error-handler
            //
            if (property.equals(Constants.ERROR_HANDLER_PROPERTY)) {
                return;
            }
            //
            // http://apache.org/xml/properties/internal/entity-manager
            //
            if (property.equals(Constants.ENTITY_MANAGER_PROPERTY)) {
                return;
            }
            //
            // http://apache.org/xml/properties/internal/entity-resolver
            //
            if (property.equals(Constants.ENTITY_RESOLVER_PROPERTY)) {
                return;
            }
            //
            // http://apache.org/xml/properties/internal/grammar-pool
            //
            if (property.equals(Constants.GRAMMAR_POOL_PROPERTY)) {
                return;
            }
            //
            // http://apache.org/xml/properties/internal/datatype-validator-factory
            //
            if (property.equals(Constants.DATATYPE_VALIDATOR_FACTORY_PROPERTY)) {
                return;
            }
        }

        //
        // Not recognized
        //

        throw new SAXNotRecognizedException(propertyId);

    } // checkProperty(String)

    // factory methods

    /** Creates an entity manager. */
    protected XMLEntityManager createEntityManager() {
        return new XMLEntityManager();
    } // createEntityManager():XMLEntityManager

    /** Creates an error reporter. */
    protected XMLErrorReporter createErrorReporter(Locator locator) {
        return new XMLErrorReporter(locator);
    } // createErrorReporter(Locator):XMLErrorReporter

} // class XMLParser
