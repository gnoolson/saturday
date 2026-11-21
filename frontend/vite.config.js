import { defineConfig } from "vite"
import vue from "@vitejs/plugin-vue"
import { resolve, dirname, basename, join, relative } from "path"
import fs from "fs"
import { fileURLToPath, URL } from "node:url"
import htmlInject from "vite-plugin-html-inject";

const DIST_FOLDER = "../www";
const STATIC_FOLDER = DIST_FOLDER + "/static";


export default defineConfig({
	plugins: [
		vue(),
		htmlInject(),
		{
			name: "copy-public-to-static",
			closeBundle() {
				const sourcePublic = resolve(import.meta.dirname, "public");
				const targetStatic = resolve(import.meta.dirname, STATIC_FOLDER);

				if (fs.existsSync(sourcePublic)) {
					fs.cpSync(sourcePublic, targetStatic, { recursive: true });
				}
			}
		},
		{
			buildStart() {
				this.addWatchFile(resolve(import.meta.dirname, "pages/doc"))
				buildMarkdownDocs()
			},
			watchChange(id) {
				if (id.endsWith(".md") && id.includes("pages/doc")) {
					buildMarkdownDocs()
				}
			},
			generateBundle() {
				buildMarkdownDocs()
			}
		}

	],
	publicDir: false,
	resolve: {
		alias: {
			"vue": "vue/dist/vue.esm-bundler.js",
			"@": fileURLToPath(new URL("./src", import.meta.url))
		}
	},
	build: {
		outDir: resolve(import.meta.dirname, DIST_FOLDER),
		assetsDir: "static",
		emptyOutDir: true,
		treeshake: false,
		rollupOptions: {
			input: {
				root: resolve(import.meta.dirname, "pages/root/index.html"),
				login: resolve(import.meta.dirname, "pages/login/index.html"),
				create_super: resolve(import.meta.dirname, "pages/create_super/index.html"),
				error: resolve(import.meta.dirname, "pages/error/index.html"),
				project_all: resolve(import.meta.dirname, "pages/project/all/index.html"),
				project_upsert: resolve(import.meta.dirname, "pages/project/upsert/index.html"),
				project_delete: resolve(import.meta.dirname, "pages/project/delete/index.html"),
				script_all: resolve(import.meta.dirname, "pages/script/all/index.html"),
				script_upsert: resolve(import.meta.dirname, "pages/script/upsert/index.html"),
				script_code: resolve(import.meta.dirname, "pages/script/code/index.html"),
				script_delete: resolve(import.meta.dirname, "pages/script/delete/index.html"),
				script_errors: resolve(import.meta.dirname, "pages/script/errors/index.html"),
				script_log: resolve(import.meta.dirname, "pages/script/log/index.html"),
				dashboard_all: resolve(import.meta.dirname, "pages/dashboard/all/index.html"),
				dashboard_upsert: resolve(import.meta.dirname, "pages/dashboard/upsert/index.html"),
				dashboard_delete: resolve(import.meta.dirname, "pages/dashboard/delete/index.html"),
				dashboard_code: resolve(import.meta.dirname, "pages/dashboard/code/index.html"),
				schedule_all: resolve(import.meta.dirname, "pages/schedule/all/index.html"),
				schedule_upsert: resolve(import.meta.dirname, "pages/schedule/upsert/index.html"),
				schedule_delete: resolve(import.meta.dirname, "pages/schedule/delete/index.html"),
				client_all: resolve(import.meta.dirname, "pages/client/all/index.html"),
				client_upsert: resolve(import.meta.dirname, "pages/client/upsert/index.html"),
				client_delete: resolve(import.meta.dirname, "pages/client/delete/index.html"),
				subscription_all: resolve(import.meta.dirname, "pages/subscription/all/index.html"),
				subscription_upsert: resolve(import.meta.dirname, "pages/subscription/upsert/index.html"),
				subscription_delete: resolve(import.meta.dirname, "pages/subscription/delete/index.html"),
				user_all: resolve(import.meta.dirname, "pages/user/all/index.html"),
				mqtt_broker: resolve(import.meta.dirname, "pages/mqtt_broker/index.html"),
				plugin_all: resolve(import.meta.dirname, "pages/plugin/all/index.html"),
				plugin_template: resolve(import.meta.dirname, "pages/plugin/template/index.html"),
				log: resolve(import.meta.dirname, "pages/log/index.html"),
				ie_export: resolve(import.meta.dirname, "pages/ie/export/index.html"),
				ie_import: resolve(import.meta.dirname, "pages/ie/import/index.html"),
				doc_import: resolve(import.meta.dirname, "pages/doc/index.html"),
			},
			output: {
				manualChunks(id) {
					if (id.includes("node_modules/vue") || id.includes("node_modules/axios")) {
						return "vendor";
					}
				}
			}
		}
	}
})


function processMarkdownIncludes(filePath, baseDir) {
	let content = fs.readFileSync(filePath, "utf-8")
	const includeRegex = /@replace\(['"](.+?)['"]\)/g

	return content.replace(includeRegex, (match, includePath) => {
		const fullIncludePath = resolve(baseDir, includePath)
		if (fs.existsSync(fullIncludePath)) {
			return processMarkdownIncludes(fullIncludePath, dirname(fullIncludePath))
		}
		console.log(`Error: File ${includePath} not found`);
		return `<!--  -->`
	})
}

function buildMarkdownDocs() {
	const srcDir = resolve(import.meta.dirname, "pages/doc")
	const outDir = resolve(import.meta.dirname, DIST_FOLDER, "pages/doc")

	if (!fs.existsSync(srcDir)) return
	if (!fs.existsSync(outDir)) {
		fs.mkdirSync(outDir, { recursive: true })
	}

	const files = fs.readdirSync(srcDir, { recursive: true })

	files.forEach(relativeFile => {
		const fullSrcPath = join(srcDir, relativeFile)

		if (!fs.statSync(fullSrcPath).isFile() || !relativeFile.endsWith(".md"))
			return

		if (basename(relativeFile).startsWith("_"))
			return

		const fullDestPath = join(outDir, relativeFile)

		fs.mkdirSync(dirname(fullDestPath), { recursive: true })

		const finalContent = processMarkdownIncludes(fullSrcPath, dirname(fullSrcPath))
		fs.writeFileSync(fullDestPath, finalContent, "utf-8")
	})
}

// import { defineConfig } from "vite"
// import vue from "@vitejs/plugin-vue"
// import { resolve } from "path"
// import fs from "fs"
// import { fileURLToPath, URL } from "node:url"
// import htmlInject from "vite-plugin-html-inject";
// import { viteStaticCopy } from "vite-plugin-static-copy"

// const DIST_FOLDER = "../www";
// const STATIC_FOLDER = DIST_FOLDER + "/static";

// export default defineConfig({
// 	plugins: [
// 		vue(),
// 		htmlInject(),
// 		{
// 			name: "copy-public-to-static",
// 			closeBundle() {
// 				const sourcePublic = resolve(import.meta.dirname, "public");
// 				const targetStatic = resolve(import.meta.dirname, STATIC_FOLDER);

// 				if (fs.existsSync(sourcePublic)) {
// 					fs.cpSync(sourcePublic, targetStatic, { recursive: true });
// 				}
// 			}
// 		},
// 		viteStaticCopy({
// 			targets: [
// 				{
// 					src: "pages/doc/**/*.md",
// 					dest: ""
// 				}
// 			],
// 			watch: {
// 				reloadPageOnChange: true
// 			}
// 		}),
// 		{
// 			name: "watch-markdown",
// 			buildStart() {
// 				this.addWatchFile(resolve(__dirname, "pages/doc"))
// 			}
// 		}
// 	],
// 	publicDir: false,
// 	resolve: {
// 		alias: {
// 			"vue": "vue/dist/vue.esm-bundler.js",
// 			"@": fileURLToPath(new URL("./src", import.meta.url))
// 		}
// 	},
// 	build: {
// 		outDir: resolve(import.meta.dirname, DIST_FOLDER),
// 		assetsDir: "static",
// 		emptyOutDir: true,
// 		treeshake: false,
// 		rollupOptions: {
// 			input: {
// 				root: resolve(import.meta.dirname, "pages/root/index.html"),

// 				login: resolve(import.meta.dirname, "pages/login/index.html"),

// 				create_super: resolve(import.meta.dirname, "pages/create_super/index.html"),

// 				error: resolve(import.meta.dirname, "pages/error/index.html"),

// 				project_all: resolve(import.meta.dirname, "pages/project/all/index.html"),
// 				project_upsert: resolve(import.meta.dirname, "pages/project/upsert/index.html"),
// 				project_delete: resolve(import.meta.dirname, "pages/project/delete/index.html"),

// 				script_all: resolve(import.meta.dirname, "pages/script/all/index.html"),
// 				script_upsert: resolve(import.meta.dirname, "pages/script/upsert/index.html"),
// 				script_code: resolve(import.meta.dirname, "pages/script/code/index.html"),
// 				script_delete: resolve(import.meta.dirname, "pages/script/delete/index.html"),
// 				script_errors: resolve(import.meta.dirname, "pages/script/errors/index.html"),
// 				script_log: resolve(import.meta.dirname, "pages/script/log/index.html"),

// 				dashboard_all: resolve(import.meta.dirname, "pages/dashboard/all/index.html"),
// 				dashboard_upsert: resolve(import.meta.dirname, "pages/dashboard/upsert/index.html"),
// 				dashboard_delete: resolve(import.meta.dirname, "pages/dashboard/delete/index.html"),
// 				dashboard_code: resolve(import.meta.dirname, "pages/dashboard/code/index.html"),

// 				schedule_all: resolve(import.meta.dirname, "pages/schedule/all/index.html"),
// 				schedule_upsert: resolve(import.meta.dirname, "pages/schedule/upsert/index.html"),
// 				schedule_delete: resolve(import.meta.dirname, "pages/schedule/delete/index.html"),

// 				client_all: resolve(import.meta.dirname, "pages/client/all/index.html"),
// 				client_upsert: resolve(import.meta.dirname, "pages/client/upsert/index.html"),
// 				client_delete: resolve(import.meta.dirname, "pages/client/delete/index.html"),

// 				subscription_all: resolve(import.meta.dirname, "pages/subscription/all/index.html"),
// 				subscription_upsert: resolve(import.meta.dirname, "pages/subscription/upsert/index.html"),
// 				subscription_delete: resolve(import.meta.dirname, "pages/subscription/delete/index.html"),

// 				user_all: resolve(import.meta.dirname, "pages/user/all/index.html"),

// 				mqtt_broker: resolve(import.meta.dirname, "pages/mqtt_broker/index.html"),

// 				plugin_all: resolve(import.meta.dirname, "pages/plugin/all/index.html"),
// 				plugin_template: resolve(import.meta.dirname, "pages/plugin/template/index.html"),

// 				log: resolve(import.meta.dirname, "pages/log/index.html"),

// 				ie_export: resolve(import.meta.dirname, "pages/ie/export/index.html"),
// 				ie_import: resolve(import.meta.dirname, "pages/ie/import/index.html"),

// 				doc_import: resolve(import.meta.dirname, "pages/doc/index.html"),
// 			},
// 			output: {
// 				manualChunks(id) {
// 					if (id.includes("node_modules/vue") || id.includes("node_modules/axios")) {
// 						return "vendor";
// 					}
// 				}
// 			}
// 		}
// 	}
// })
