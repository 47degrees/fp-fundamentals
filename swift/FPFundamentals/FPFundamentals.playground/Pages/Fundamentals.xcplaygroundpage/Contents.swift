import Bow

// Data types

// Maybe === Option

// Typeclasses

// Combinator === Semigroup
// Transformer === Functor
// Transformer2 + Lifter === Applicative
// Flattener === Monad

// Instances

// Int and Option provide their instances
// Program

struct Account {
    let id: String
    let balance: Int
}

struct Statement {
    let isRich: Bool
    let accounts: Int
}

class Program {
    let getBank1Credentials: Option<String> = .some("MyUser_Password")
    let moneyInPocket: Int = 20
    
    func getBalanceBank1(credentials: String) -> Option<Account> {
        return .some(Account(id: "a1", balance: 100))
    }
    
    let getBalanceBank2: Option<Int> = .some(80)
    
    var b1: Option<Int> {
        return Option<Any>.monad().flatMap(self.getBank1Credentials, { credentials in
            Option<Any>.functor().map(self.getBalanceBank1(credentials: credentials), { acc in acc.balance }).fix()
        }).fix()
    }
    
    var b2: Option<Int> { return self.getBalanceBank2 }
    
    var p: Option<Int> {
        return Option<Any>.applicative().pure(moneyInPocket).fix()
    }
    
    var balance: Option<Int> {
        let combinator = Option.semigroup(Int.sumSemigroup)
        return combinator.combine(combinator.combine(b1, b2), p).fix()
    }
    
    var statement: Option<Statement> {
        return Option<Any>.applicative().map2(b1, b2) { x, y in
            Statement(isRich: (x + y > 1000), accounts: 2) }.fix()
    }
    
    func run() {
        print(balance)
        print(statement)
    }
}

Program().run()
