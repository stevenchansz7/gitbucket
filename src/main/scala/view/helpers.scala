package view
import java.util.Date
import java.text.SimpleDateFormat
import twirl.api.Html

object helpers {
  
  /**
   * Format java.util.Date to "yyyy/MM/dd HH:mm:ss".
   */
  def datetime(date: Date): String = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date)
  
  /**
   * Format java.util.Date to "yyyy/MM/dd".
   */
  def date(date: Date): String = new SimpleDateFormat("yyyy/MM/dd").format(date)

  def format(value: String): Html =
    Html(value.replaceAll(" ", "&nbsp;").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;").replaceAll("\n", "<br>"))

  // TODO escape html tags using HtmlEscapeUtils (Commons Lang)
  def formatCommitLog(message: String): Html = {
    val i = message.trim.indexOf("\n")
    val (firstLine, description) = if(i >= 0){
      (message.trim.substring(0, i).trim, Some(message.trim.substring(i).trim))
    } else {
      (message.trim, None)
    }

    val sb = new StringBuilder()
    sb.append("<div class=\"summary\">").append(format(firstLine).text).append("</div>")
    if(description.isDefined){
      sb.append("<div class=\"description\">").append(format(description.get).text).append("</div>")
    }

    Html(sb.toString)
  }

  /**
   * Converts Markdown of Wiki pages to HTML.
   */
  def markdown(value: String, repository: service.RepositoryService.RepositoryInfo,
               enableWikiLink: Boolean, enableCommitLink: Boolean, enableIssueLink: Boolean)(implicit context: app.Context): Html = {
    Html(Markdown.toHtml(value, repository, enableWikiLink, enableCommitLink, enableIssueLink))
  }
  
//  /**
//   * Cut the given string by specified length.
//   */
//  def cut(message: String, length: Int): String = {
//    if(message.length > length){
//      message.substring(0, length) + "..."
//    } else {
//      message
//    }
//  }
  
}