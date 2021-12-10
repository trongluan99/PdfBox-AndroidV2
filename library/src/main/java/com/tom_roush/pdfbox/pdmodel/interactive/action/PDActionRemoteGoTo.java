/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tom_roush.pdfbox.pdmodel.interactive.action;

import java.io.IOException;

import com.tom_roush.pdfbox.cos.COSBase;
import com.tom_roush.pdfbox.cos.COSBoolean;
import com.tom_roush.pdfbox.cos.COSDictionary;
import com.tom_roush.pdfbox.cos.COSName;
import com.tom_roush.pdfbox.pdmodel.common.filespecification.PDFileSpecification;

/**
 * This represents a remote go-to action that can be executed in a PDF document.
 *
 * @author Ben Litchfield
 * @author Panagiotis Toumasis
 */
public class PDActionRemoteGoTo extends PDAction
{
    /**
     * This type of action this object represents.
     */
    public static final String SUB_TYPE = "GoToR";

    /**
     * Default constructor.
     */
    public PDActionRemoteGoTo()
    {
        setSubType( SUB_TYPE );
    }

    /**
     * Constructor.
     *
     * @param a The action dictionary.
     */
    public PDActionRemoteGoTo( COSDictionary a )
    {
        super( a );
    }

    /**
     * This will get the type of action that the actions dictionary describes.
     * It must be GoToR for a remote go-to action.
     *
     * @return The S entry of the specific remote go-to action dictionary.
     * @deprecated use {@link #getSubType() }.
     */
    @Deprecated
    public String getS()
    {
        return action.getNameAsString( COSName.S );
    }

    /**
     * This will set the type of action that the actions dictionary describes.
     * It must be GoToR for a remote go-to action.
     *
     * @param s The remote go-to action.
     * @deprecated use {@link #setSubType(java.lang.String) }.
     */
    @Deprecated
    public void setS( String s )
    {
        action.setName( COSName.S, s );
    }

    /**
     * This will get the file in which the destination is located.
     *
     * @return The F entry of the specific remote go-to action dictionary.
     *
     * @throws IOException If there is an error creating the file spec.
     */
    public PDFileSpecification getFile() throws IOException
    {
        return PDFileSpecification.createFS( action.getDictionaryObject( COSName.F ) );
    }

    /**
     * This will set the file in which the destination is located.
     *
     * @param fs The file specification.
     */
    public void setFile( PDFileSpecification fs )
    {
        action.setItem( COSName.F, fs );
    }

    /**
     * This will get the destination to jump to.
     * If the value is an array defining an explicit destination,
     * its first element must be a page number within the remote
     * document rather than an indirect reference to a page object
     * in the current document. The first page is numbered 0.
     *
     * @return The D entry of the specific remote go-to action dictionary.
     */

    // Array or String.
    public COSBase getD()
    {
        return action.getDictionaryObject( COSName.D );
    }

    /**
     * This will set the destination to jump to.
     * If the value is an array defining an explicit destination,
     * its first element must be a page number within the remote
     * document rather than an indirect reference to a page object
     * in the current document. The first page is numbered 0.
     *
     * @param d The destination.
     */

    // In case the value is an array.
    public void setD( COSBase d )
    {
        action.setItem( COSName.D, d );
    }

    /**
     * This will specify whether to open the destination document in a new window.
     * If this flag is false, the destination document will replace the current
     * document in the same window. If this entry is absent, the viewer application
     * should behave in accordance with the current user preference.
     *
     * @return A flag specifying whether to open the destination document in a new window.
     *
     * @deprecated use {@link #getOpenInNewWindow()}
     */
    @Deprecated
    public boolean shouldOpenInNewWindow()
    {
        return action.getBoolean(COSName.NEW_WINDOW, true );
    }

    /**
     * This will specify the destination document to open in a new window.
     *
     * @param value The flag value.
     *
     * @deprecated use {@link #setOpenInNewWindow(OpenMode)}
     */
    @Deprecated
    public void setOpenInNewWindow( boolean value )
    {
        action.setBoolean(COSName.NEW_WINDOW, value );
    }

    /**
     * This will specify whether to open the destination document in a new window, in the same
     * window, or behave in accordance with the current user preference.
     *
     * @return A flag specifying how to open the destination document.
     */
    public OpenMode getOpenInNewWindow()
    {
        if (getCOSObject().getDictionaryObject(COSName.NEW_WINDOW) instanceof COSBoolean)
        {
            COSBoolean b = (COSBoolean) getCOSObject().getDictionaryObject(COSName.NEW_WINDOW);
            return b.getValue() ? OpenMode.NEW_WINDOW : OpenMode.SAME_WINDOW;
        }
        return OpenMode.USER_PREFERENCE;
    }

    /**
     * This will specify whether to open the destination document in a new window.
     *
     * @param value The flag value.
     */
    public void setOpenInNewWindow(OpenMode value)
    {
        if (null == value)
        {
            getCOSObject().removeItem(COSName.NEW_WINDOW);
            return;
        }
        switch (value)
        {
            case USER_PREFERENCE:
                getCOSObject().removeItem(COSName.NEW_WINDOW);
                break;
            case SAME_WINDOW:
                getCOSObject().setBoolean(COSName.NEW_WINDOW, false);
                break;
            case NEW_WINDOW:
                getCOSObject().setBoolean(COSName.NEW_WINDOW, true);
                break;
            default:
                // shouldn't happen unless the enum type is changed
                break;
        }
    }
}
