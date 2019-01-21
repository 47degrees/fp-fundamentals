
// Data types



// Typeclasses



// Instances



// Program

class Program {
    let getBalanceBank1: Int = 100
    let getBalanceBank2: Int = 80
    
    var balance: Int {
        let b1: Int = getBalanceBank1
        let b2: Int = getBalanceBank2
        
        return b1 + b2
    }
    
    func run() {
        print(balance)
    }
}

Program().run()
