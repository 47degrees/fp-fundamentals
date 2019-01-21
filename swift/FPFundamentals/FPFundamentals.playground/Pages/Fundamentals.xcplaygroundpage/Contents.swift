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

protocol Transformer {
    associatedtype F
    
    func map<A, B>(_ fa: Kind<F, A>, _ f: @escaping (A) -> B) -> Kind<F, B>
}

protocol Transformer2: Transformer {
    func ap<A, B>(_ fa: Kind<F, A>, _ ff: Kind<F, (A) -> B>) -> Kind<F, B>
}

extension Transformer2 {
    func product<A, B>(_ fa: Kind<F, A>, _ fb: Kind<F, B>) -> Kind<F, (A, B)> {
        return ap(fb, map(fa, { a in { b in (a, b) } }))
    }
    
    func map2<A, B, Z>(_ fa: Kind<F, A>, _ fb: Kind<F, B>, _ f: @escaping (A, B) -> Z) -> Kind<F, Z> {
        return map(product(fa, fb), f)
    }
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

class MaybeTransformer: Transformer {
    typealias F = ForMaybe
    
    func map<A, B>(_ fa: Kind<ForMaybe, A>, _ f: @escaping (A) -> B) -> Kind<ForMaybe, B> {
        return fa.fix().fold({ Maybe<B>.no() },
                             { a in Maybe<B>.yes(f(a)) })
    }
}

class MaybeTransformer2: MaybeTransformer, Transformer2 {
    func ap<A, B>(_ fa: Kind<ForMaybe, A>, _ ff: Kind<ForMaybe, (A) -> B>) -> Kind<ForMaybe, B> {
        return ff.fix().fold({ Maybe<B>.no() },
                             { f in fa.fix().fold({ Maybe<B>.no() },
                                                  { a in Maybe.yes(f(a)) })
        })
    }
}

extension Maybe {
    static func combinator<CV>(_ cv: CV) -> MaybeCombinator<A, CV> where CV: Combinator, CV.A == A {
        return MaybeCombinator(cv)
    }
    
    static func transformer() -> MaybeTransformer {
        return MaybeTransformer()
    }
    
    static func transformer2() -> MaybeTransformer2 {
        return MaybeTransformer2()
    }
}

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
    let getBalanceBank1: Maybe<Account> = .yes(Account(id: "a1", balance: 100))
    let getBalanceBank2: Maybe<Int> = .yes(80)
    
    var b1: Maybe<Int> {
        return Maybe<Any>.transformer().map(getBalanceBank1, { acc in acc.balance }).fix()
    }
    
    var b2: Maybe<Int> { return self.getBalanceBank2 }
    
    var balance: Maybe<Int> {
        return Maybe.combinator(Int.combinator).combine(b1, b2)
    }
    
    var statement: Maybe<Statement> {
        return Maybe<Any>.transformer2().map2(b1, b2) { x, y in
            Statement(isRich: (x + y > 1000), accounts: 2) }.fix()
    }
    
    func run() {
        print(balance)
        print(statement)
    }
}

Program().run()
