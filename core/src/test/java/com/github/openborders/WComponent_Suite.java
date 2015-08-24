package com.github.openborders;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.openborders.container.Container_Suite;
import com.github.openborders.layout.Layout_Suite;
import com.github.openborders.monitor.Monitor_Suite;
import com.github.openborders.registry.Registry_Suite;
import com.github.openborders.render.Render_Suite;
import com.github.openborders.servlet.Servlet_Suite;
import com.github.openborders.subordinate.Subordinate_Suite;
import com.github.openborders.testapp.TestApp_Test;
import com.github.openborders.theme.ThemeUtil_Test;
import com.github.openborders.util.Util_Suite;
import com.github.openborders.validation.Validation_Suite;
import com.github.openborders.validator.Validator_Suite;
import com.github.openborders.velocity.Velocity_Suite;

/**
 * This class is the <a href="http://www.junit.org">JUnit</a> TestSuite for the classes within
 * {@link com.github.openborders} package.
 * 
 * @author Martin Shevchenko
 * @since 1.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses
({
    Container_Suite.class,
    Layout_Suite.class,
    Monitor_Suite.class,
    Registry_Suite.class,
    Render_Suite.class,
    Servlet_Suite.class,
    Subordinate_Suite.class,
    Util_Suite.class,
    Validation_Suite.class,
    Validator_Suite.class,
    Velocity_Suite.class,
    
    AbstractBeanTableDataModel_Test.class,
    AbstractContainer_Test.class,
    AbstractInput_Test.class,
    AbstractNamingContextContainer_Test.class,
    AbstractMutableContainer_Test.class,
    
    AudioResource_Test.class,
    CollapsibleGroup_Test.class,
    ContentEscape_Test.class,
    ForwardException_Test.class,
    HeadersImpl_Test.class,
    ImageResource_Test.class,
    InternalResource_Test.class,
    Margin_Test.class,
    Message_Test.class,
    OptionGroup_Test.class,
    RadioButtonGroup_Test.class,
    Serialization_Test.class,
    SerializationPerformance_Test.class,
    SimpleBeanListTableDataModel_Test.class,
    SimpleWComponentTest.class,
    UserAgentInfo_Test.class,
    VideoResource_Test.class,
    WCardManager_Test.class,
    WAbbrText_Test.class,
    WAjaxControl_Test.class,
    WApplication_Test.class,
    WAudio_Test.class,
    WBeanComponent_Test.class,
    WBeanContainer_Test.class,
    WButton_Test.class,
    WCheckBox_Test.class,
    WDefinitionList_Test.class,
    AbstractWComponent_Test.class,
    WContent_Test.class,
    WContentLink_Test.class,
    WComponentGroup_Test.class,
    WComponentPerformance_Test.class,
    WDropdown_Test.class,
    WFileWidget_Test.class,
    WMultiFileWidget_Test.class,
    WImage_Test.class,
    WWindow_Test.class,
    WLabel_Test.class,
    WLink_Test.class,
    WList_Test.class,
    WMessages_Test.class,
    WMessagesProxy_Test.class,
    WRadioButton_Test.class,
    WRepeater_Test.class,
    WRow_Test.class,
    WSection_Test.class,
    WShuffler_Test.class,
    WCancelButton_Test.class,
    WCheckBoxSelect_Test.class,
    WCollapsibleToggle_Test.class,
    WCollapsible_Test.class,
    WColumnLayout_Test.class,
    WColumn_Test.class,
    WConfirmationButton_Test.class,
    WDateField_Test.class,
    WDecoratedLabel_Test.class,
    WEmailField_Test.class,
    WFieldLayout_Test.class,
    WField_Test.class,
    WFieldSet_Test.class,
    WFigure_Test.class,
    WFilterControl_Test.class,
    WHeading_Test.class,
    WMessageBox_Test.class,
    WMenu_Test.class,
    WMenuItem_Test.class,
    WMenuItemGroup_Test.class,
    WNumberField_Test.class,
    WPasswordField_Test.class,
    WPhoneNumberField_Test.class,
    WStyledText_Text.class,
    WSubMenu_Test.class,
    WSuggestions_Test.class,
    WTab_Test.class,
    WTabSet_Test.class,
    WMultiDropdown_Test.class,
    WPanel_Test.class,
    WProgressBar_Test.class,
    WPrintButton_Test.class,
    WRadioButtonSelect_Test.class,
    WSelectToggle_Test.class,
    WMultiTextField_Test.class,
    WNamingContext_Test.class,
    WPartialDateField_Test.class,
    WTable_Test.class,
    WTextArea_Test.class,
    WTextField_Test.class,
    WText_Test.class,
    WVideo_Test.class,
    WebUtilities_Test.class,

    BeanBoundComponentModel_Test.class,
    BeanProviderBoundComponentModel_Test.class,
    FatalErrorPage_Test.class,
    FatalErrorPageFactoryImpl_Test.class,
    InitialisationException_Test.class,
    WDialog_Test.class,
    WLink_Test.class,
    WPopup_Test.class,
    WInternalLink_Test.class,
    WFilterText_Test.class,
    IntegrityException_Test.class,
    WMultiSelect_Test.class,
    WMultiSelectPair_Test.class,
    WSingleSelect_Test.class,
    UicProfileButton_Test.class,
    UIContextDebugWrapper_Test.class,
    UIContextImpl_Test.class,
    AbstractWSelectList_Test.class,
    AbstractWSingleSelectList_Test.class,
    AbstractWMultiSelectList_Test.class,
    SelectListUtil_Test.class,
    
    RowIdList_Test.class,
    SimpleTableDataModel_Test.class,
    TableTreeNode_Test.class,
    WDataTable_Test.class,
    WTableColumn_Test.class,        
    AbstractTreeTableDataModel_Test.class,
    
    TestApp_Test.class,
    
    ThemeUtil_Test.class
})
public class WComponent_Suite
{
}
