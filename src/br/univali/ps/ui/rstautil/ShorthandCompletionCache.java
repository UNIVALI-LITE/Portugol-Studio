/*
 * 07/22/2012
 *
 * Copyright (C) 2012 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is distributed under a modified BSD license.  See the included
 * RSTALanguageSupport.License.txt file for details.
 */
package br.univali.ps.ui.rstautil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fife.ui.autocomplete.AbstractCompletionProvider;
import org.fife.ui.autocomplete.Completion;


/**
 * A cache to store completions for Template completions and Comment
 * completions.  Template completions should extend
 * <code>TemplateCompletion</code> that uses parameterized variables/values.<p> 
 * 
 * While template completion example:
 * <pre>
 * while --&gt; while(condition) {
 *              //cursor here
 *           }
 * </pre>
 * 
 * Comment completion example:
 * <pre>
 * null --&gt; &lt;code&gt;null&lt;/code&gt;
 * </pre> 
 * 
 * This is really a convenient place to store these types of completions that
 * are re-used.
 * 
 * @author Steve
 */
public class ShorthandCompletionCache {
	
	private ArrayList shorthandCompletion = new ArrayList();
	private ArrayList commentCompletion = new ArrayList();
	
	private AbstractCompletionProvider templateProvider, commentProvider;


	public ShorthandCompletionCache(AbstractCompletionProvider templateProvider,
			AbstractCompletionProvider commentProvider) {
		this.templateProvider = templateProvider;
		this.commentProvider = commentProvider;
	}
	
	public void addShorthandCompletion(Completion completion) {
		addSorted(shorthandCompletion, completion);
	}


	private static final void addSorted(List list, Completion completion) {
		int index = Collections.binarySearch(list, completion);
		if (index<0) {
			// index = -insertion_point - 1
			index = -(index+1);
		}
		list.add(index, completion);
	}


	public List getShorthandCompletions() {
		return shorthandCompletion;
	}
	
	public void removeShorthandCompletion(Completion completion) {
		shorthandCompletion.remove(completion);
	}
	
	public void clearCache() {
		shorthandCompletion.clear();
	}
	
	//comments
	public void addCommentCompletion(Completion completion) {
		addSorted(commentCompletion, completion);
	}

	public List getCommentCompletions() {
		return commentCompletion;
	}
	
	public void removeCommentCompletion(Completion completion) {
		commentCompletion.remove(completion);
	}
	
	public AbstractCompletionProvider getTemplateProvider() {
		return templateProvider;
	}
	
	public AbstractCompletionProvider getCommentProvider() {
		return commentProvider;
	}

}