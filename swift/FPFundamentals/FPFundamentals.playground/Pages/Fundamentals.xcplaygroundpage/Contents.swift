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

protocol Combinator {
    associatedtype A
    
    func combine(_ x: A, _ y: A) -> A
}

// Instances

class IntCombinator: Combinator {
    typealias A = Int
    
    func combine(_ x: Int, _ y: Int) -> Int {
        return x + y
    }
}

extension Int {
    static var combinator: IntCombinator {
        return IntCombinator()
    }
}

class MaybeCombinator<V, CV>: Combinator where CV: Combinator, CV.A == V {
    typealias A = Maybe<V>
    
    let combinator: CV
    
    init(_ combinator: CV) {
        self.combinator = combinator
    }
    
    func combine(_ x: Maybe<V>, _ y: Maybe<V>) -> Maybe<V> {
        return x.fold(
            { y },
            { xx in y.fold(
                { x },
                { yy in Maybe.yes(combinator.combine(xx, yy))
            })
        })
    }
}

extension Maybe {
    static func combinator<CV>(_ cv: CV) -> MaybeCombinator<A, CV> where CV: Combinator, CV.A == A {
        return MaybeCombinator(cv)
    }
}

// Program

class Program {
    let getBalanceBank1: Maybe<Int> = .yes(100)
    let getBalanceBank2: Maybe<Int> = .yes(80)
    
    var balance: Maybe<Int> {
        let b1: Maybe<Int> = getBalanceBank1
        let b2: Maybe<Int> = getBalanceBank2
        
        return Maybe.combinator(Int.combinator).combine(b1, b2)
    }
    
    func run() {
        print(balance)
    }
}

Program().run()
