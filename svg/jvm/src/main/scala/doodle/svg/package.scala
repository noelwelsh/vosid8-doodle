package doodle

import cats.data.Writer
import doodle.algebra.generic.reified.{Reification,Reified}

package object svg {
  type Algebra = doodle.svg.algebra.Algebra.type
  type Drawing[A] = doodle.algebra.generic.Finalized[Reification,A]
  type Renderable[A] = doodle.algebra.generic.Renderable[Writer[List[Reified],?],A]

  type SvgFrame = Unit
  implicit val svgWriter = doodle.svg.effect.SvgWriter

  type Image[A] = doodle.algebra.Image[Algebra, Drawing, A]
  object Image {
    def apply(f: Algebra => Drawing[Unit]): Image[Unit] = {
      new Image[Unit] {
        def apply(implicit algebra: Algebra): Drawing[Unit] =
          f(algebra)
      }
    }
  }
}
