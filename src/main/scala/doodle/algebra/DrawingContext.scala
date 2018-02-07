/*
 * Copyright 2015 noelwelsh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package doodle
package algebra

import cats.syntax.all._
import cats.instances.option._
import doodle.core.Color

final case class Stroke(color: Color, width: Double)
final case class Fill(color: Color)

/** Stores state about the current drawing style.
  *
  * The type `B` is the type of Blends.
  */
final case class DrawingContext[B](
  blendMode: Option[B],
  strokeWidth: Option[Double],
  strokeColor: Option[Color],
  fillColor: Option[Color]
) {
  def blendMode(mode: B): DrawingContext[B] =
    this.copy(blendMode = blendMode orElse Some(mode))


  def stroke: Option[Stroke] =
    (strokeColor, strokeWidth).mapN((c, w) => Stroke(c, w))

  def strokeColor(color: Color): DrawingContext[B] =
    this.copy(strokeColor = strokeColor orElse Some(color))

  def strokeWidth(width: Double): DrawingContext[B] =
    this.copy(strokeWidth = strokeWidth orElse { if(width <= 0) None else Some(width) })

  def noStroke: DrawingContext[B] =
    this.copy(strokeWidth = strokeWidth orElse None)


  def fill: Option[Fill] =
    fillColor.map(c => Fill(c))

  def fillColor(color: Color): DrawingContext[B] =
    this.copy(fillColor = fillColor orElse Some(color))

  def noFill: DrawingContext[B] =
    this.copy(fillColor = fillColor orElse None)
}
object DrawingContext {
  def empty[B]: DrawingContext[B] = DrawingContext(None,None,None,None)
}
