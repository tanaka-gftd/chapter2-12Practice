//N予備校 Scala学習2-12 中級問題
/*
  DayOfWeek型を使って、
  ある日の次の曜日を返すメソッドnextDayOfWeekをDayOfWeekオブジェクトに追加実装する。
*/


/*
  ScalaかIDEAの仕様変更なのか、
  N予備校のカリキュラム(2017年頃作成されたもの)の記載と違い、
  クラス宣言にsealedを付けて、そのクラスを継承したobjectにcaseを付けても、
  パターンマッチ時の網羅漏れの警告が出ない。
  でも、カリキュラムで習った内容を復習するため、一応つけておく。
*/
//DayOfWeekクラスを抽象クラス(abstract)として宣言し、このファイルの中でのみ使用できるようsealedを付ける
sealed abstract class DayOfWeek

//DayOfWeekクラスを継承したオブジェクトを宣言(各曜日に対応)
//網羅漏れ対策に、caseを付けておく(ただし、上述のように警告は出なくなった模様)
case object Sunday extends DayOfWeek
case object Monday extends DayOfWeek
case object Tuesday extends DayOfWeek
case object Wednesday extends DayOfWeek
case object Thursday extends DayOfWeek
case object Friday extends DayOfWeek
case object Saturday extends DayOfWeek


object DayOfWeek { //コンパニオンメソッドだが、DayOfWeekクラスにはプライベートなものがないので、名前を同じにしなくても問題なし

  //nextDayOfWeek()に曜日を渡すと、次の曜日を返すメソッドを設定
  //引数、返り値は共にDayOfWeek型
  def nextDayOfWeek(d: DayOfWeek): DayOfWeek = {
    d match {
      case Sunday => Monday
      case Monday => Tuesday
      case Tuesday => Wednesday
      case Wednesday => Thursday
      case Thursday => Friday
      case Friday => Saturday
      case Saturday => Sunday
    }
  }
}
