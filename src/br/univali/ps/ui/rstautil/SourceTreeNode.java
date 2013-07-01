/*
 * 10/09/2011
 *
 * Copyright (C) 2011 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is distributed under a modified BSD license.  See the included
 * RSTALanguageSupport.License.txt file for details.
 */
package br.univali.ps.ui.rstautil;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.fife.ui.autocomplete.Util;


/**
 * Base class for tree nodes in an {@link AbstractSourceTree}.  They can be
 * sorted and filtered based on user input.
 *
 * @author Robert Futrell
 * @version 1.0
 * @see AbstractSourceTree
 */
public class SourceTreeNode extends DefaultMutableTreeNode
		implements Cloneable, Comparable {

	private boolean sortable;
	private boolean sorted;
	private Pattern pattern;
	private Vector visibleChildren;
	private int sortPriority;


	/**
	 * Creates an unsorted tree node.
	 *
	 * @param userObject The user data for this tree node.
	 */
	public SourceTreeNode(Object userObject) {
		this(userObject, false);
	}


	/**
	 * Constructor.
	 *
	 * @param userObject The user data for this tree node.
	 * @param sorted Whether any child nodes added to this one should be
	 *        sorted.
	 */
	public SourceTreeNode(Object userObject, boolean sorted) {
		super(userObject);
		visibleChildren = new Vector();
		setSortable(true);
		setSorted(sorted);
	}


	/**
	 * Overridden to ensure the new child is only made visible if it is
	 * matched by the current filter.
	 *
	 * @param child The child node to add.
	 */
	public void add(MutableTreeNode child) {
		//super.add(child);
		if(child!=null && child.getParent()==this) {
			insert(child, super.getChildCount() - 1);
		}
		else {
			insert(child, super.getChildCount());
		}
		if (sortable && sorted) {
			refreshVisibleChildren(); // TODO: Find index and add for performance
		}
	}


	/**
	 * Overridden to operate over visible children only.
	 *
	 * @return The visible children.
	 */
	public Enumeration children() {
		return visibleChildren.elements();
	}


	/**
	 * Returns a clone of this tree node.  The clone will not contain any child
	 * nodes.
	 *
	 * @return The clone of this node.
	 * @see #cloneWithChildren()
	 */
	public Object clone() {
		SourceTreeNode node = (SourceTreeNode)super.clone();
		// Not based off original, no children!
		node.visibleChildren = new Vector();
		return node;
	}


	/**
	 * Returns a clone of this tree node and all of its children.
	 *
	 * @return The clone of this node.
	 * @see #clone()
	 */
	public SourceTreeNode cloneWithChildren() {
		SourceTreeNode clone = (SourceTreeNode)clone();
		for (int i=0; i<super.getChildCount(); i++) {
			clone.add(((SourceTreeNode)super.getChildAt(i)).cloneWithChildren());
		}
		return clone;
	}


	/**
	 * Overridden to provide proper sorting of source tree nodes when the
	 * parent <code>AbstractSourceTree</code> is sorted.  Sorting is done first
	 * by priority, and nodes with equal priority are then sorted by their
	 * string representations, ignoring case.  Subclasses can override this
	 * method if they wish to do more intricate sorting.
	 *
	 * @param obj A tree node to compare to.
	 * @return How these tree nodes compare relative to each other.
	 */
	public int compareTo(Object obj) {
		int res = -1;
		if (obj instanceof SourceTreeNode) {
			SourceTreeNode stn2 = (SourceTreeNode)obj;
			res = getSortPriority() - stn2.getSortPriority();
			if (res==0 && ((SourceTreeNode)getParent()).isSorted()) {
				res = toString().compareToIgnoreCase(stn2.toString());
			}
		}
		return res;
	}


	/**
	 * Filters the children of this tree node based on the specified prefix.
	 *
	 * @param pattern The pattern that the child nodes must match.  If this is
	 *        <code>null</code>, all possible children are shown.
	 */
	protected void filter(Pattern pattern) {
		this.pattern = pattern;
		refreshVisibleChildren();
		for (int i=0; i<super.getChildCount(); i++) {
			Object child = children.get(i);
			if (child instanceof SourceTreeNode) {
				((SourceTreeNode)child).filter(pattern);
			}
		}
	}


	/**
	 * Overridden to operate over visible children only.
	 *
	 * @param child The child node.
	 * @return The visible child after the specified node, or <code>null</code>
	 *         if none.
	 */
	public TreeNode getChildAfter(TreeNode child) {
		if (child==null) {
			throw new IllegalArgumentException("child cannot be null");
		}
		int index = getIndex(child);
		if (index==-1) {
			throw new IllegalArgumentException("child node not contained");
		}
		return index<getChildCount()-1 ? getChildAt(index+1) : null;
	}


	/**
	 * Overridden to operate over visible children only.
	 *
	 * @param index The index of the visible child to retrieve.
	 * @return The visible child after the specified index.
	 */
	public TreeNode getChildAt(int index) {
		//System.out.println(index);
		return (TreeNode)visibleChildren.get(index);
	}


	/**
	 * Overridden to operate over visible children only.
	 *
	 * @param child The child node.
	 * @return The visible child before the specified node, or <code>null</code>
	 *         if none.
	 */
	public TreeNode getChildBefore(TreeNode child) {
		if (child==null) {
			throw new IllegalArgumentException("child cannot be null");
		}
		int index = getIndex(child);
		if (index==-1) {
			throw new IllegalArgumentException("child node not contained");
		}
		return index> 0 ? getChildAt(index - 1) : null;
	}


	/**
	 * Overridden to operate over visible children only.
	 *
	 * @return The number of visible child nodes.
	 */
	public int getChildCount() {
		return visibleChildren.size();
	}


	/**
	 * Overridden to operate over visible children only.
	 *
	 * @param child The child node.
	 * @return The index of the child, if it is visible.  If the child node is
	 *         not contained in this tree, or is simply not visible,
	 *         <code>-1</code> is returned.
	 */
	public int getIndex(TreeNode child) {
		if (child==null) {
			throw new IllegalArgumentException("child cannot be null");
		}
		for (int i=0; i<visibleChildren.size(); i++) {
			TreeNode node = (TreeNode)visibleChildren.get(i);
			if (node.equals(child)) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * Returns the relative priority of this node against others when being
	 * sorted (lower is higher priority).
	 *
	 * @return The relative priority.
	 * @see #setSortPriority(int)
	 */
	public int getSortPriority() {
		return sortPriority;
	}


    /**
	 * Returns whether this particular node's children can be sorted.
	 *
	 * @return Whether this node's children can be sorted.
	 * @see #setSortable(boolean)
	 */
	public boolean isSortable() {
		return sortable;
	}


	/**
	 * Returns whether this node is sorted.
	 *
	 * @return Whether this node is sorted.
	 */
	public boolean isSorted() {
		return sorted;
	}


	protected void refresh() {
		refreshVisibleChildren();
		for (int i=0; i<getChildCount(); i++) {
			TreeNode child = getChildAt(i);
			if (child instanceof SourceTreeNode) {
				((SourceTreeNode)child).refresh();
			}
		}
	}


	/**
	 * Refreshes what children are visible in the tree.
	 */
	private void refreshVisibleChildren() {
		visibleChildren.clear();
		if (children!=null) {
			visibleChildren.addAll(children);
			if (sortable && sorted) {
				Collections.sort(visibleChildren);
			}
			if (pattern!=null) {
				for (Iterator i=visibleChildren.iterator(); i.hasNext(); ) {
					TreeNode node = (TreeNode)i.next();
					if (node.isLeaf()) {
						String text = node.toString();
						text = Util.stripHtml(text);
						if (!pattern.matcher(text).find()) {
							//System.out.println(pattern + ": Removing tree node: " + text);
							i.remove();
						}
					}
				}
			}
		}
	}


	/**
	 * Sets whether this particular node's children are sortable.  Usually,
	 * only tree nodes containing only "leaves" should be sorted (for example,
	 * a "types" node).
	 *
	 * @param sortable Whether this node's children are sortable.
	 * @see #isSortable()
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}


	/**
	 * Sets whether this tree node (and any child sortable tree nodes) are
	 * sorting their children.
	 *
	 * @param sorted Whether sorting is enabled.
	 * @see #isSorted()
	 */
	public void setSorted(boolean sorted) {
		if (sorted!=this.sorted) {
			// We must keep this state, even if we're not sortable, so that
			// we can know when to toggle the sortable state of our children.
			this.sorted = sorted;
			// This individual node may not be sortable...
			if (sortable) {
				refreshVisibleChildren();
			}
			// But its children could still be.
			for (int i=0; i<super.getChildCount(); i++) {
				Object child = children.get(i);
				if (child instanceof SourceTreeNode) {
					((SourceTreeNode)child).setSorted(sorted);
				}
			}
		}
	}


	/**
	 * Sets the relative sort priority of this tree node when it is compared
	 * against others (lower is higher priority).
	 *
	 * @param priority The relative priority.
	 * @see #getSortPriority()
	 */
	public void setSortPriority(int priority) {
		this.sortPriority = priority;
	}


}