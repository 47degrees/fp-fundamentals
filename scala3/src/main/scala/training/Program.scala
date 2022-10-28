package training

@main def hello: Unit =
  def getBalanceBank1: Int = 100
  def getBalanceBank2: Int = 80
  def balance: Int =
    val b1: Int = getBalanceBank1
    val b2: Int = getBalanceBank2
    b1 + b2
  // EDGE OF THE WORLD
  println(balance)
