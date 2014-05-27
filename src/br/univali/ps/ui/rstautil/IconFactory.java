/*
 * 03/21/2010
 *
 * Copyright (C) 2010 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is distributed under a modified BSD license.  See the included
 * RSTALanguageSupport.License.txt file for details.
 */
package br.univali.ps.ui.rstautil;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;



/**
 * Holds icons used by Java auto-completion.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class IconFactory {

	public static final String SOURCE_FILE_ICON			= "sourceFileIcon";
	public static final String PACKAGE_ICON				= "packageIcon";
	public static final String IMPORT_ROOT_ICON			= "importRootIcon";
	public static final String IMPORT_ICON				= "importIcon";
	public static final String DEFAULT_CLASS_ICON		= "defaultClassIcon";
	public static final String DEFAULT_INTERFACE_ICON	= "defaultInterfaceIcon";
	public static final String CLASS_ICON				= "classIcon";
	public static final String ENUM_ICON				= "enumIcon";
	public static final String ENUM_PROTECTED_ICON		= "enumProtectedIcon";
	public static final String ENUM_PRIVATE_ICON		= "enumPrivateIcon";
	public static final String ENUM_DEFAULT_ICON		= "enumDefaultIcon";
	public static final String INNER_CLASS_PUBLIC_ICON		= "innerClassPublicIcon";
	public static final String INNER_CLASS_PROTECTED_ICON	= "innerClassProtectedIcon";
	public static final String INNER_CLASS_PRIVATE_ICON	= "innerClassPrivateIcon";
	public static final String INNER_CLASS_DEFAULT_ICON	= "innerClassDefaultIcon";
	public static final String INTERFACE_ICON			= "interfaceIcon";
	public static final String JAVADOC_ITEM_ICON		= "javadocItemIcon";
	public static final String LOCAL_VARIABLE_ICON		= "localVariableIcon";
	public static final String METHOD_PUBLIC_ICON		= "methodPublicIcon";
	public static final String METHOD_PROTECTED_ICON	= "methodProtectedIcon";
	public static final String METHOD_PRIVATE_ICON		= "methodPrivateIcon";
	public static final String METHOD_DEFAULT_ICON		= "methodDefaultIcon";
	public static final String TEMPLATE_ICON			= "templateIcon";
	public static final String FIELD_PUBLIC_ICON		= "fieldPublicIcon";
	public static final String FIELD_PROTECTED_ICON		= "fieldProtectedIcon";
	public static final String FIELD_PRIVATE_ICON		= "fieldPrivateIcon";
	public static final String FIELD_DEFAULT_ICON		= "fieldDefaultIcon";

	public static final String CONSTRUCTOR_ICON			= "constructorIcon";
	public static final String DEPRECATED_ICON			= "deprecatedIcon";
	public static final String ABSTRACT_ICON			= "abstractIcon";
	public static final String FINAL_ICON				= "finalIcon";
	public static final String STATIC_ICON				= "staticIcon";

	private Map iconMap;

	private static final IconFactory INSTANCE = new IconFactory();


	private IconFactory() {

		iconMap = new HashMap();
		iconMap.put(SOURCE_FILE_ICON, loadIcon("jcu_obj.gif"));
		iconMap.put(PACKAGE_ICON, loadIcon("package_obj.gif"));
		iconMap.put(IMPORT_ROOT_ICON, loadIcon("impc_obj.gif"));
		iconMap.put(IMPORT_ICON, loadIcon("imp_obj.gif"));
		iconMap.put(DEFAULT_CLASS_ICON, loadIcon("class_default_obj.gif"));
		iconMap.put(DEFAULT_INTERFACE_ICON, loadIcon("int_default_obj.gif"));
		iconMap.put(CLASS_ICON, loadIcon("class_obj.gif"));
		iconMap.put(ENUM_ICON, loadIcon("enum_obj.gif"));
		iconMap.put(ENUM_PROTECTED_ICON, loadIcon("enum_protected_obj.gif"));
		iconMap.put(ENUM_PRIVATE_ICON, loadIcon("enum_private_obj.gif"));
		iconMap.put(ENUM_DEFAULT_ICON, loadIcon("enum_default_obj.gif"));
		iconMap.put(INNER_CLASS_PUBLIC_ICON, loadIcon("innerclass_public_obj.gif"));
		iconMap.put(INNER_CLASS_PROTECTED_ICON, loadIcon("innerclass_protected_obj.gif"));
		iconMap.put(INNER_CLASS_PRIVATE_ICON, loadIcon("innerclass_private_obj.gif"));
		iconMap.put(INNER_CLASS_DEFAULT_ICON, loadIcon("innerclass_default_obj.gif"));
		iconMap.put(INTERFACE_ICON, loadIcon("int_obj.gif"));
		iconMap.put(JAVADOC_ITEM_ICON, loadIcon("jdoc_tag_obj.gif"));
		iconMap.put(LOCAL_VARIABLE_ICON, loadIcon("localvariable_obj.gif"));
		iconMap.put(METHOD_PUBLIC_ICON, loadIcon("methpub_obj.gif"));
		iconMap.put(METHOD_PROTECTED_ICON, loadIcon("methpro_obj.gif"));
		iconMap.put(METHOD_PRIVATE_ICON, loadIcon("methpri_obj.gif"));
		iconMap.put(METHOD_DEFAULT_ICON, loadIcon("methdef_obj.gif"));
		iconMap.put(TEMPLATE_ICON, loadIcon("template_obj.gif"));
		iconMap.put(FIELD_PUBLIC_ICON, loadIcon("field_public_obj.gif"));
		iconMap.put(FIELD_PROTECTED_ICON, loadIcon("field_protected_obj.gif"));
		iconMap.put(FIELD_PRIVATE_ICON, loadIcon("field_private_obj.gif"));
		iconMap.put(FIELD_DEFAULT_ICON, loadIcon("field_default_obj.gif"));

		iconMap.put(CONSTRUCTOR_ICON, loadIcon("constr_ovr.gif"));
		iconMap.put(DEPRECATED_ICON, loadIcon("deprecated.gif"));
		iconMap.put(ABSTRACT_ICON, loadIcon("abstract_co.gif"));
		iconMap.put(FINAL_ICON, loadIcon("final_co.gif"));
		iconMap.put(STATIC_ICON, loadIcon("static_co.gif"));

	}


	public Icon getIcon(String key) {
		return getIcon(key, false);
	}


	public Icon getIcon(String key, boolean deprecated) {
		Icon icon = (Icon)iconMap.get(key);
		if (deprecated) { // TODO: Optimize me
			DecoratableIcon di = new DecoratableIcon(16, icon);
			di.setDeprecated(deprecated);
			icon = di;
		}
		return icon;
	}


	public Icon getIcon(IconData data) {
		// TODO: Optimize me
		DecoratableIcon icon = new DecoratableIcon(16, getIcon(data.getIcon()));
		icon.setDeprecated(data.isDeprecated());
		if (data.isAbstract()) {
			icon.addDecorationIcon(getIcon(ABSTRACT_ICON));
		}
		if (data.isStatic()) {
			icon.addDecorationIcon(getIcon(STATIC_ICON));
		}
		if (data.isFinal()) {
			icon.addDecorationIcon(getIcon(FINAL_ICON));
		}
		return icon;
	}


	public static IconFactory get() {
		return INSTANCE;
	}


	private Icon loadIcon(String name) {
		URL res = getClass().getResource("img/" + name);
		if (res==null) {
			// IllegalArgumentException is what would be thrown if res
			// was null anyway, we're just giving the actual arg name to
			// make the message more descriptive
			throw new IllegalArgumentException("icon not found: img/" + name);
		}
		return new ImageIcon(res);
	}


	public static interface IconData {

		/**
		 * Returns the main icon to use when rendering this member's completion.
		 * This icon will be decorated appropriately based on whether it is
		 * abstract, deprecated, final, static, or any of the above.
		 *
		 * @return The icon to use.
		 */
		public String getIcon();

		public boolean isAbstract();

		public boolean isDeprecated();

		public boolean isFinal();

		public boolean isStatic();

	}


}