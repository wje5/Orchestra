package com.zonesoft.orchestra.mixin;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.extensions.IForgeVertexBuilder;

@Mixin(BufferBuilder.class)
public abstract class MixinBufferBuilder implements IForgeVertexBuilder {
	@Override
	public void addVertexData(MatrixStack.Entry matrixStack, BakedQuad bakedQuad, float red, float green, float blue,
			int lightmapCoord, int overlayColor, boolean readExistingColor) {
		addVertexData(matrixStack, bakedQuad, red, green, blue, 1.0f, lightmapCoord, overlayColor, readExistingColor);
	}

	@Override
	public void addVertexData(MatrixStack.Entry matrixEntry, BakedQuad bakedQuad, float red, float green, float blue,
			float alpha, int lightmapCoord, int overlayColor) {
		addVertexData(matrixEntry, bakedQuad, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, red, green, blue, alpha,
				new int[] { lightmapCoord, lightmapCoord, lightmapCoord, lightmapCoord }, overlayColor, false);
	}

	@Override
	public void addVertexData(MatrixStack.Entry matrixEntry, BakedQuad bakedQuad, float red, float green, float blue,
			float alpha, int lightmapCoord, int overlayColor, boolean readExistingColor) {
		addVertexData(matrixEntry, bakedQuad, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, red, green, blue, alpha,
				new int[] { lightmapCoord, lightmapCoord, lightmapCoord, lightmapCoord }, overlayColor,
				readExistingColor);
	}

	@Override
	public void addVertexData(MatrixStack.Entry matrixEntry, BakedQuad bakedQuad, float[] baseBrightness, float red,
			float green, float blue, float alpha, int[] lightmapCoords, int overlayCoords, boolean readExistingColor) {
		long start = System.nanoTime();
		int[] aint = bakedQuad.getVertexData();
		Vector3i faceNormal = bakedQuad.getFace().getDirectionVec();
		Vector3f normal = new Vector3f(faceNormal.getX(), faceNormal.getY(), faceNormal.getZ());
		Matrix4f matrix4f = matrixEntry.getMatrix();
		normal.transform(matrixEntry.getNormal());
		int intSize = DefaultVertexFormats.BLOCK.getIntegerSize();
		int vertexCount = aint.length / intSize;

		try (MemoryStack memorystack = MemoryStack.stackPush()) {
			ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormats.BLOCK.getSize());
			IntBuffer intbuffer = bytebuffer.asIntBuffer();

			for (int v = 0; v < vertexCount; ++v) {
				((Buffer) intbuffer).clear();
				intbuffer.put(aint, v * 8, 8);
				float f = bytebuffer.getFloat(0);
				float f1 = bytebuffer.getFloat(4);
				float f2 = bytebuffer.getFloat(8);
				float cr;
				float cg;
				float cb;
				float ca;
				if (readExistingColor) {
					float r = (bytebuffer.get(12) & 255) / 255.0F;
					float g = (bytebuffer.get(13) & 255) / 255.0F;
					float b = (bytebuffer.get(14) & 255) / 255.0F;
					float a = (bytebuffer.get(15) & 255) / 255.0F;
					cr = r * baseBrightness[v] * red;
					cg = g * baseBrightness[v] * green;
					cb = b * baseBrightness[v] * blue;
					ca = a * alpha;
				} else {
					cr = baseBrightness[v] * red;
					cg = baseBrightness[v] * green;
					cb = baseBrightness[v] * blue;
					ca = alpha;
				}

				int lightmapCoord = applyBakedLighting(lightmapCoords[v], bytebuffer);
				float f9 = bytebuffer.getFloat(16);
				float f10 = bytebuffer.getFloat(20);
				Vector4f pos = new Vector4f(f, f1, f2, 1.0F);
				pos.transform(matrix4f);
				applyBakedNormals(normal, bytebuffer, matrixEntry.getNormal());
				((IVertexBuilder) this).addVertex(pos.getX(), pos.getY(), pos.getZ(), cr, cg, cb, ca, f9, f10,
						overlayCoords, lightmapCoord, normal.getX(), normal.getY(), normal.getZ());
			}
		}
	}
}
