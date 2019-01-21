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

class Program<F, MonadF> where MonadF: Monad, MonadF.F == F {
    let monad: MonadF
    
    init(monad: MonadF) {
        self.monad = monad
    }
    
    var getBank1Credentials: Kind<F, String> {
        return monad.pure("MyUser_Password")
    }
    let moneyInPocket: Int = 20
    
    func getBalanceBank1(credentials: String) -> Kind<F, Account> {
        return self.monad.pure(Account(id: "a1", balance: 100))
    }
    
    var getBalanceBank2: Kind<F, Int> {
        return self.monad.pure(80)
    }
    
    var balance: Kind<F, Int> {
        return monad.binding(
            { self.getBank1Credentials },
            { credentials in self.monad.map(self.getBalanceBank1(credentials: credentials), { acc in acc.balance }) },
            { _, _ in self.getBalanceBank2 },
            { _, _, _ in self.monad.pure(self.moneyInPocket) },
            { _, b1, b2, p in self.monad.pure(b1 + b2 + p) }
        )
    }
    
    func run() {
        print(balance)
    }
}

Program(monad: Id<Any>.monad()).run()
Program(monad: Option<Any>.monad()).run()
Program(monad: Either<String, Any>.monad()).run()
Program(monad: Try<Any>.monad()).run()
Program(monad: ListK<Any>.monad()).run()
