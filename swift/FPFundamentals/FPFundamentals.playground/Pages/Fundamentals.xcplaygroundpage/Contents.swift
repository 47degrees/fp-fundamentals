import Bow

// Data types
class ForMaybe {}
typealias MaybeOf<A> = Kind<ForMaybe, A>

class Maybe<A>: MaybeOf<A> {
    static func fix(_ value: MaybeOf<A>) -> Maybe<A> {
        return value as! Maybe<A>
    }
    
    static func yes(_ a: A) -> Maybe<A> {
        return Yes(a)
    }
    
    static func no() -> Maybe<A> {
        return No()
    }
    
    func fold<B>(_ ifAbsent: () -> B, _ ifPresent: (A) -> B) -> B {
        switch self {
        case is No<A>: return ifAbsent()
        case is Yes<A>:
            let yes = self as! Yes
            return ifPresent(yes.value)
        default:
            fatalError("Unreachable code")
        }
    }
}

class Yes<A>: Maybe<A> {
    let value: A
    
    init(_ value: A) {
        self.value = value
    }
}

class No<A>: Maybe<A> {}

extension Kind where F == ForMaybe {
    func fix() -> Maybe<A> {
        return Maybe<A>.fix(self)
    }
}

extension Maybe: CustomStringConvertible {
    var description: String {
        return self.fold({ "No" },
                         { value in "Yes(\(value))" })
    }
}

// Typeclasses



// Instances



// Program

class Program {
    let getBalanceBank1: Maybe<Int> = .yes(100)
    let getBalanceBank2: Maybe<Int> = .yes(80)
    
    var balance: Maybe<Int> {
        let b1: Maybe<Int> = getBalanceBank1
        let b2: Maybe<Int> = getBalanceBank2
        
        return b1 + b2 // It won't compile
    }
    
    func run() {
        print(balance)
    }
}

Program().run()
