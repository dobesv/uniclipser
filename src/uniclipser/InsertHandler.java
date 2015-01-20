package uniclipser;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class InsertHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
		        .getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		IEditorPart part = page.getActiveEditor();
		try {
			if (part instanceof AbstractTextEditor) {
				ITextEditor editor = (ITextEditor) part;
				ISelectionProvider selectionProvider = editor
				        .getSelectionProvider();
				ISelection selection = selectionProvider.getSelection();
				if (selection instanceof ITextSelection) {
					ITextSelection textSelection = (ITextSelection) selection;
					IDocumentProvider dp = editor.getDocumentProvider();
					IDocument doc = dp.getDocument(editor.getEditorInput());
					final String replacement = event.getCommand().getName();
					doc.replace(textSelection.getOffset(), textSelection.getLength(), replacement);
					editor.selectAndReveal(textSelection.getOffset()+replacement.length(), 0);
				} else {
					System.out
			        .println("Inside that handler! - did not insert - no selection");
				}
			} else {
				System.out
				        .println("Inside that handler! - did not insert - not a text editor");
			}
		} catch (NotDefinedException e) {
			e.printStackTrace();
			System.out
			        .println("Inside that handler! - did not insert - exception");
		} catch (BadLocationException e) {
			e.printStackTrace();
			System.out
			        .println("Inside that handler! - did not insert - exception");
		}
		return null;
	}
}
