
// Data types

enum Maybe<A> {
    case yes(A)
    case no
}

// Typeclasses



// Instances



// Program

class Program {
    let getBalanceBank1: Maybe<Int> = .yes(100)
    let getBalanceBank2: Maybe<Int> = .yes(80)
    
    var balance: Int {
        let b1: Maybe<Int> = getBalanceBank1
        let b2: Maybe<Int> = getBalanceBank2
        
        return b1 + b2
    }
    
    func run() {
        print(balance)
    }
}

Program().run()
