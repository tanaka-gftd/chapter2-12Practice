//N予備校 Scala学習2-12 上級問題
/*
  二分木（子の数が最大で 2 つであるような木構造）を表す型 Tree と Branch, Empty を実装し、
    •最大値を求めるmaxメソッド
    •最小値を求めるminメソッド
    •深さを求めるdepthメソッド
  を実装する
*/




sealed abstract class Tree
case class Branch(value: Int, left: Tree, right: Tree) extends Tree
case object Empty extends Tree
/*
  問題文より、
  子が 2 つで左の子の値が2、右の子の値が3、自分自身の値が1の木構造はたとえば次のようにして定義できます。

  val tree: Tree = Branch(1, Branch(2, Empty, Empty), Branch(3, Empty, Empty))
*/


//最大値を求めるmaxメソッド
//分からなかったので、maxの実装だけ解答を参考にし、その解答をヒントに他の関数を自力で実装しました
//親ノードが持つ子ノードの数に応じて、パターン分けするのがコツのようです
object BinaryTree {

  def max(tree: Tree): Int = tree match {

    //子ノードなし
    case Branch(value, Empty, Empty) => value

    //子ノード(左側のみ)
    case Branch(value, left, Empty) =>
      val x = max(left)
      if(value > x) value else x

    //子ノード(右側のみ)
    case Branch(value, Empty, right) =>
      val x = max(right)
      if(value > x) value else x

    //子ノード(左右両方にある場合)
    case Branch(value, left, right) =>
      val x = max(left)
      val y = max(right)
      if(value > x && value > y) {
        value
      } else if (x > y && x > value) {
        x
      } else {
        y
      }

    //Emptyの時用に、例外処理も付ける
    //このコードがないと、scala.MatchError: Empty (of class Empty$) になる
    case Empty => throw new RuntimeException
  }


  //最小値を求めるminメソッド
  //実装方法はmaxと同様(大小は逆)
  def min(tree: Tree): Int = tree match {
    case Branch(value, Empty, Empty) => value
    case Branch(value, left, Empty) =>
      val x = min(left)
      if (value < x) value else x
    case Branch(value, Empty, right) =>
      val x = min(right)
      if (value < x) value else x
    case Branch(value, left, right) =>
      val x = min(left)
      val y = min(right)
      if (value < x && value < y) {
        value
      } else if (x < y && x < value) {
        x
      } else {
        y
      }
    case Empty => throw new RuntimeException
  }


  //深さを求めるdepthメソッド
  //自力で実装 この内容で問題ないが、模範解答をみる限り、改善すべき点が多い
  /*
    模範解答をみる限り、子ノードの所有でのパターン分けは不要らしい。
    子ノード所持でも、子ノード未所持でも再起的呼び出しをしている。
     → 存在しない子ノードに対して再帰敵呼び出しを行っても、case Empty => 0 で処理するため、エラーにはならない。
    また、深さを求める際は各ノードが持つ値(以下の実装だとvalue)は意味を成さないので、
    _(ワイルドカード)でキャッチする方がいい。
  */
  def depth(tree: Tree): Int = tree match {

    //Emptyの深さは0とする
    case Empty => 0

    //子ノードを持たない場合、1
    case Branch(value, Empty, Empty)  => 1

    //左側にのみ子ノードを持つ場合、深さを+1して再帰敵呼び出して深く進む(階層を一つ進んだので+1する)
    case Branch(value, left, Empty) =>
      depth(left) + 1

    //右側にのみ子ノードを持つ場合、再帰敵呼び出しをして深く進む(階層を一つ進んだので+1する)
    case Branch(value, Empty, right) =>
      depth(right) + 1

    //左右両側に子ノードを持つ場合、左右の深さを比較して、より深い方を返す
    case Branch(value, left, right) =>
      val x = depth(left) + 1
      val y = depth(right) + 1
      if(x > y) x else y

    //階層を進んだ際に=+することを鑑みれば、値を返すタイミングで+1した方がいいかも
      /*
      val x = depth(left)
      val y = depth(right)
      (if(x > y) x  else y) + 1  //このように括弧でくくることで、返る値がxでもyでも+1される
      */
  }



  //以下、N予備校での模範解答
  /*
  //maxの模範解答
  def max(t: Tree): Int = t match {
      case Branch(v, Empty, Empty) =>
        v
      case Branch(v, Empty, r) =>
        val x = max(r)
        if(v > x) v else x
      case Branch(v, l, Empty) =>
        val x = max(l)
        if(v > x) v else x
      case Branch(v, l, r) =>
        val x = max(l)
        val y = max(r)
        if(v > x) {
          if(v > y) v else y
        } else {
          if(x > y) x else y
        }
      case Empty =>
        throw new RuntimeException
    }


  //minの模範解答
  def min(t: Tree): Int = t match {
      case Branch(v, Empty, Empty) =>
        v
      case Branch(v, Empty, r) =>
        val x = min(r)
        if(v < x) v else x
      case Branch(v, l, Empty) =>
        val x = min(l)
        if(v < x) v else x
      case Branch(v, l, r) =>
        val x = min(l)
        val y = min(r)
        if(v < x) {
          if(v < y) v else y
        } else {
          if(x < y) x else y
        }
      case Empty =>
        throw new RuntimeException
    }


  //depthの模範解答
  def depth(t: Tree): Int = t match {
      case Empty => 0
      case Branch(_, l, r) =>
        val ldepth = depth(l)
        val rdepth = depth(r)
        (if(ldepth < rdepth) rdepth else ldepth) + 1
    }
  */
}