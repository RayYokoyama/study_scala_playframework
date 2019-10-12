# 簡単なブログアプリでscalaとplayframeworkの勉強するためのレポジトリ


# 起動
### sbt run
### mysql -uroot -p
### mysql.server start


# 調べること
* railsで言うところのmigrationファイルはevolution/default/#{i}.sql
　　→これは一回実行した後はどうする？
　　→tableがすでにあるとエラー吐くから別フォルダに置いてる

* controllerにhtmlを書く感じがすごく気持ち悪い
* model.rb的なのがないからDBにScopeとかどこに書こう
  →そもそも ActiveRecordじゃない時点でscopeとかは書きようがあるんかな
  →あるやろうけどSQL直で描くのもすごい嫌輩何かAPI探す