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

import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.impl.XMLDTDScanner;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.impl.validation.grammars.DTDGrammar;
import org.xml.sax.SAXException;

/**
 * @author Stubs generated by DesignDoc on Mon Sep 11 11:10:57 PDT 2000
 * @version $Id$
 */
public class DTDParser
    extends XMLGrammarParser
    implements XMLDTDHandler, XMLDTDContentModelHandler {

    //
    // Data
    //

    /** fDTDScanner */
    protected XMLDTDScanner fDTDScanner;

    //
    // Constructors
    //

    /**
     * 
     * 
     * @param symbolTable 
     */
    public DTDParser(SymbolTable symbolTable) {
        super(symbolTable);
    }

    //
    // Methods
    //

    /**
     * getDTDGrammar
     * 
     * @return 
     */
    public DTDGrammar getDTDGrammar() {
        return null;
    } // getDTDGrammar

    //
    // XMLEntityHandler methods
    //

    /**
     * startEntity
     * 
     * @param name 
     * @param publicId 
     * @param systemId 
     */
    public void startEntity(String name, String publicId, String systemId)
        throws SAXException {
    }

    /**
     * endEntity
     * 
     * @param name 
     */
    public void endEntity(String name)
        throws SAXException {
    }

    //
    // XMLDTDHandler methods
    //

    /**
     * startDTD
     */
    public void startDTD()
        throws SAXException {
    } // startDTD

    /**
     * comment
     * 
     * @param text 
     */
    public void comment(XMLString text)
        throws SAXException {
    } // comment

    /**
     * processingInstruction
     * 
     * @param target 
     * @param data 
     */
    public void processingInstruction(String target, XMLString data)
        throws SAXException {
    } // processingInstruction

    /**
     * startExternalSubset
     */
    public void startExternalSubset()
        throws SAXException {
    } // startExternalSubset

    /**
     * endExternalSubset
     */
    public void endExternalSubset()
        throws SAXException {
    } // endExternalSubset

    /**
     * elementDecl
     * 
     * @param name 
     * @param contentModel 
     */
    public void elementDecl(String name, XMLString contentModel)
        throws SAXException {
    } // elementDecl

    /**
     * startAttlist
     * 
     * @param elementName 
     */
    public void startAttlist(String elementName)
        throws SAXException {
    } // startAttlist

    /**
     * attributeDecl
     * 
     * @param elementName 
     * @param attributeName 
     * @param type 
     * @param enumeration 
     * @param defaultType 
     * @param defaultValue 
     */
    public void attributeDecl(String elementName, String attributeName, String type, String[] enumeration, String defaultType, XMLString defaultValue)
        throws SAXException {
    } // attributeDecl

    /**
     * endAttlist
     */
    public void endAttlist()
        throws SAXException {
    } // endAttlist

    /**
     * internalEntityDecl
     * 
     * @param name 
     * @param text 
     * @param isPE 
     */
    public void internalEntityDecl(String name, XMLString text, boolean isPE)
        throws SAXException {
    } // internalEntityDecl

    /**
     * externalEntityDecl
     * 
     * @param name 
     * @param publicId 
     * @param systemId 
     * @param isPE 
     */
    public void externalEntityDecl(String name, String publicId, String systemId, boolean isPE)
        throws SAXException {
    } // externalEntityDecl

    /**
     * unparsedEntityDecl
     * 
     * @param name 
     * @param publicId 
     * @param systemId 
     * @param notation 
     */
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notation)
        throws SAXException {
    } // unparsedEntityDecl

    /**
     * notationDecl
     * 
     * @param name 
     * @param publicId 
     * @param systemId 
     */
    public void notationDecl(String name, String publicId, String systemId)
        throws SAXException {
    } // notationDecl

    /**
     * startConditional
     * 
     * @param type 
     */
    public void startConditional(short type)
        throws SAXException {
    } // startConditional

    /**
     * endConditional
     */
    public void endConditional()
        throws SAXException {
    } // endConditional

    /**
     * endDTD
     */
    public void endDTD()
        throws SAXException {
    } // endDTD

    //
    // XMLDTDContentModelHandler methods
    //

    /**
     * startContentModel
     * 
     * @param elementName 
     * @param type 
     */
    public void startContentModel(String elementName, short type)
        throws SAXException {
    } // startContentModel

    /**
     * mixedElement
     * 
     * @param elementName 
     */
    public void mixedElement(String elementName)
        throws SAXException {
    } // mixedElement

    /**
     * childrenStartGroup
     */
    public void childrenStartGroup()
        throws SAXException {
    } // childrenStartGroup

    /**
     * childrenElement
     * 
     * @param elementName 
     */
    public void childrenElement(String elementName)
        throws SAXException {
    } // childrenElement

    /**
     * childrenSeparator
     * 
     * @param separator 
     */
    public void childrenSeparator(short separator)
        throws SAXException {
    } // childrenSeparator

    /**
     * childrenOccurrence
     * 
     * @param occurrence 
     */
    public void childrenOccurrence(short occurrence)
        throws SAXException {
    } // childrenOccurrence

    /**
     * childrenEndGroup
     */
    public void childrenEndGroup()
        throws SAXException {
    } // childrenEndGroup

    /**
     * endContentModel
     */
    public void endContentModel()
        throws SAXException {
    } // endContentModel

} // class DTDParser
